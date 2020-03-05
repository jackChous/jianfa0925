package day18

import day16.hw.{CalculateUtil, IPUtils, JedisConnectionPool}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, HasOffsetRanges, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import redis.clients.jedis.JedisCluster

/**
  * redis 保存Offset
  */
object KafkaRedis2Offset extends App {
//  Logger.getLogger("org").setLevel(Level.WARN)
  private val conf: SparkConf = new SparkConf().setAppName("DirectKafa").setMaster("local[*]")
  private val ssc = new StreamingContext(conf,Seconds(3))
  val broadcastRef = IPUtils.broadcastIpRules(ssc, "file:///D:\\千峰\\第二阶段\\学习内容\\数仓项目和spark\\day19-IP、搜狗案例\\ipsearch\\ip.txt")

  val BOOTSTRAP_SERVER = "hadoop01:9092"
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
  private val jedis: JedisCluster = JedisConnectionPool.getConnection()

//获取redis内部的Offset
  private val fromOffset = JedisOffset(GROUP_ID)

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

  stream.foreachRDD(rdd=>{
    if (!rdd.isEmpty()) {
      //为了后续更新Offset做准备
      val offsetRangs = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      //    逻辑处理

      val lines: RDD[String] = rdd.map(_.value())

      val fields: RDD[Array[String]] = lines.map(_.split(" "))

      //计算成交总金额
      CalculateUtil.calculateIncome(fields)

      //计算商品分类金额
      CalculateUtil.calculateItem(fields)

      //计算区域成交金额
      CalculateUtil.calculateZone(fields, broadcastRef)
      //    更新偏移量
      for (o <- offsetRangs) {
        jedis.hset(GROUP_ID, o.topic + "-" + o.partition, o.untilOffset.toString)
      }
    }
  })
  ssc.start()
  ssc.awaitTermination()
}
