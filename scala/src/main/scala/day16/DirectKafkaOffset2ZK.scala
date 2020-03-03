package day16

import day15.SparkStreaming_KafkaWC.{kafkaParams, topics}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, HasOffsetRanges, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object DirectKafkaOffset2ZK {
  def main(args: Array[String]): Unit = {
//    屏蔽log
    Logger.getLogger("org").setLevel(Level.WARN)
    val conf = new SparkConf().setAppName("DirectKafka").setMaster("local[*]")
    val ssc = new StreamingContext(conf,Seconds(3))
//    配置节点信息
    val BOOTSTRAP_SERVER = "hadoop01:9092"
    val ZK_SERVER ="hadoop01:2181"
//    消费者组
    val GROUP_ID ="sz_consymer"
//    topic
    val topics = Array("sz1901Tests")
//    配置Kafka参数
    val kafkaParams = Map[String,Object](
      "bootstrap.servers" -> BOOTSTRAP_SERVER,
      //    kafka需要配置解码器
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      //    配置消费者组
      "group.id" -> GROUP_ID,
      //    设置从头消费
      "auto.offset.reset" -> "latest",
      //    设置是否自动提交offset
      "enable.auto.commit"->"false"
    )
//    采用Zookeeper手动维护Offset
    val zkManager = new KafkaOffsetZK(ZK_SERVER)
//    获取offset
    val fromOffset = zkManager.getFromOffset(topics,GROUP_ID)

//    创建数据流
    var stream:InputDStream[ConsumerRecord[String,String]] =null
//    判断   如果不是第一次读取数据，offset大于0
    if(fromOffset.size > 0){
      stream =KafkaUtils.createDirectStream(
        ssc,
        LocationStrategies.PreferConsistent,
        ConsumerStrategies.Subscribe[String, String](topics, kafkaParams,fromOffset)
      )

    }else{
//      如果是第一次消费数据  从0开始读取
      stream =KafkaUtils.createDirectStream[String,String](
        ssc,
        LocationStrategies.PreferConsistent,
        ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)
      )
      println("第一次消费数据")
    }
//    处理数据流
    stream.foreachRDD(rdd=>{
      val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
//      输出
      rdd.foreach(t=>println(t.value()))
//      更新offset
      zkManager.UpdateOffset(offsetRanges,GROUP_ID)
    })
//    启动程序
    ssc.start()
    ssc.awaitTermination()
  }
}
