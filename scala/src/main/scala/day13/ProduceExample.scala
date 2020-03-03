package day13

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}


object ProduceExample extends App {
  private val properties = new Properties()
  val TOPIC = "yangxin_test1"
  properties.put("bootstrap.servers", "hadoop01:9092")
  properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  properties.put("metadata.broker.list", "hadoop01:9092")
  properties.put("group.id", "something")
  val producer = new KafkaProducer[String, String](properties)
  for ( i <- 1 to 50){
    val record = new ProducerRecord(TOPIC, "key", s"hello $i")
    println(record)
    producer.send(record)
  }
  val record = new ProducerRecord(TOPIC, "key", "the end " + new java.util.Date)
  producer.send(record)
  println(record)
  producer.close()
}

