package day13

import java.util
import java.util.Properties
import org.apache.kafka.clients.consumer.KafkaConsumer
import scala.collection.JavaConverters._

object ConsumerExample extends App {
  val TOPIC = "yangxin_test1"
  val props = new Properties()
  props.put("bootstrap.servers", "hadoop01:9092")
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("group.id", "something")
  val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(util.Collections.singletonList(TOPIC))
  while(true) {
    val records = consumer.poll(100)
    for (record <- records.asScala) {
      println(record)
    }
  }


}
