package day16;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;

/**
 * 发送数据（生产者）
 */
public class CollectLog10 {
   public static void main(String[] args){
       //这个是用来配置kafka的参数
       Properties prop = new Properties();
       //这里不是配置broker.id了，这个是配置bootstrap.servers
       prop.put("bootstrap.servers", "hadoop01:9092");
       //下面是分别配置 key和value的序列化
       prop.put("key.serializer",
               "org.apache.kafka.common.serialization.StringSerializer");
       prop.put("value.serializer",
               "org.apache.kafka.common.serialization.StringSerializer");
       //这个地方和1.0X之前的版本有不一样的，这个是使用kafkaproducer 类来实例化
       Producer<String, String> producer = new KafkaProducer(prop);
       try {
           BufferedReader bf = new BufferedReader(
                   new FileReader(
                           new File(
                                   "D:\\千峰\\第二阶段\\学习内容\\数仓项目和spark\\" +
                                           "day28-redis伪集群\\笔记\\order.log")));// 路径
           String line = null;
           while((line=bf.readLine())!=null){
               Thread.sleep(1000);
               producer.send(
                       new ProducerRecord<String, String>(
                               "sz1901Tests", line));
           }
           bf.close();
           producer.close();
           System.out.println("已经发送完毕");
       } catch (Exception e) {
           e.printStackTrace();
       }
//       while(true){
//           producer.send(
//                   new ProducerRecord<String, String>(
//                           "sz1901Tests", "123"));
//       }

   }
}
