package day16.hw

import day15.SparkStreaming_KafkaWC.ssc
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Consumer {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("filter").setMaster("local[*]")
    val ssc = new StreamingContext(conf,Seconds(3))
    val broadcastRef = IPUtils.broadcastIpRules(ssc, "D:\\千峰\\第二阶段\\学习内容\\数仓项目和spark\\day19-IP、搜狗案例\\ipsearch\\ip.txt")
    //  topic
    val topics = Array("sz1901Tests")

    //  配置kafka的参数
   val kafkaParams: Map[String, Object] =Map[String,Object](
     "bootstrap.servers" -> "hadoop01:9092",
     //    kafka需要配置解码器
     "key.deserializer" -> classOf[StringDeserializer],
     "value.deserializer" -> classOf[StringDeserializer],
     //    配置消费者组
     "group.id" -> "sz01",
     //    设置从头消费
     "auto.offset.reset" -> "earliest"
   )
    //  设置kafka数据流
    val inputDStream:InputDStream[ConsumerRecord[String,String]]=
    KafkaUtils.createDirectStream(
      ssc,
      //    均匀地将Kafka内的分区数据分配到各个SparkStreaming中的Executor内
      //    保证数据分配均匀
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)
    )
    // 数据流进行过滤
    val reulst = inputDStream.foreachRDD { kafkaRDD =>
      if (!kafkaRDD.isEmpty()) {
        val lines: RDD[String] = kafkaRDD.map(_.value())

        val fields: RDD[Array[String]] = lines.map(_.split(" "))


        //计算成交总金额
        CalculateUtil.calculateIncome(fields)

        //计算商品分类金额
        CalculateUtil.calculateItem(fields)

        //计算区域成交金额
        CalculateUtil.calculateZone(fields, broadcastRef)

      }
    }



    // 启动
    ssc.start()
    ssc.awaitTermination()
  }
}
