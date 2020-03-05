package day13

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.kafka010.KafkaUtils

object SparkStreaming02_KafkaSource extends App {
  val ints =List(1,2,3,4,5,6)

  private val intses: Iterator[List[Int]] = ints.sliding(2,2)
  for (list <- intses) {
    println(list.mkString(","))
  }

  /**
//使用SparkStreaming完成WordCount
//  Spark配置对象
  private val conf: SparkConf = new SparkConf().setAppName("wc").setMaster("local[*]")
//实时数据分析环境对象
//  采集周期，以指定的时间为周期采集实时数据
  private val ssc = new StreamingContext(conf,Seconds(5))





//  从kafka中采集数据
  private val data: Any = KafkaUtils.createDirectStream(ssc, zkQuorum, group, topicMap)




//  将采集的数据进行分解（扁平化）
  private val wordDStream: DStream[String] = kafkaDStream.flatMap(line=>line.split(","))
//  将数据进行结构的转换方便统计分析
  private val mapDStream: DStream[(String, Int)] = wordDStream.map((_,1))
  private val wordToSumDStream: DStream[(String, Int)] = mapDStream.reduceByKey(_+_)

//  将结果打印
  wordToSumDStream.print()

//  不能停止采集程序
//  streamingContext.stop()
//  启动采集器
streamingContext.start()
//  Driver等待采集器的执行
streamingContext.awaitTermination()


    **/
}

