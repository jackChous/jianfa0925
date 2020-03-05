package day13

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}

object SparkStreaming01_WC extends App {
//使用SparkStreaming完成WordCount
//  Spark配置对象
  private val conf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")
//实时数据分析环境对象
//  设置执行入口及采集周期，以指定的时间为周期采集实时数据
  private val streamingContext = new StreamingContext(conf,Seconds(5))
//  首先，获取当前批次的数据流，也就是将InputData转换成DStream
  private val socketLineDStream: ReceiverInputDStream[String] = streamingContext.socketTextStream("hadoop01",9999,StorageLevel.MEMORY_ONLY)

//  将采集的数据进行分解（扁平化）
  private val wordDStream: DStream[String] = socketLineDStream.flatMap(line=>line.split(","))
//  将数据进行结构的转换方便统计分析
  private val mapDStream: DStream[(String, Int)] = wordDStream.map((_,1))
  private val wordToSumDStream: DStream[(String, Int)] = mapDStream.reduceByKey(_+_)

//  将结果打印
  wordToSumDStream.print()


//  不能停止采集程序
//  streamingContext.stop()
//  启动采集器
streamingContext.start()
//  Driver等待采集器的执行,等待停止
streamingContext.awaitTermination()


}
