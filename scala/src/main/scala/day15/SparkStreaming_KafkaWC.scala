package day15

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreaming_KafkaWC extends App {

    //  屏蔽log
    Logger.getLogger("org").setLevel(Level.WARN)

    private val conf = new SparkConf().setAppName("kafka").setMaster("local[*]")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    private val ssc = new StreamingContext(conf, Seconds(5))

    //  topic
    private val topics =Array("sz1901Tests")

    //  配置kafka的参数
    private val kafkaParams: Map[String, Object] =
    Map[String, Object](
      "bootstrap.servers" -> "hadoop01:9092",
      //    kafka需要配置解码器
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      //    配置消费者组
      "group.id" -> "sz01",
      //    设置从头消费
      "auto.offset.reset" -> "earliest"
      //    设置是否自动提交offset
    )
    //  设置kafka数据流
    private val inputDStream: InputDStream[ConsumerRecord[String, String]] =
    KafkaUtils.createDirectStream(
      ssc,
      //    均匀地将Kafka内的分区数据分配到各个SparkStreaming中的Executor内
      //    保证数据分配均匀
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)
    )

    inputDStream.map(_.value()).print()
    //  启动程序
    ssc.start()
    ssc.awaitTermination()
  }
