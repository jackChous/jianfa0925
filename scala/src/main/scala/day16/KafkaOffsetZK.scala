package day16

import org.apache.curator.framework.CuratorFrameworkFactory
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.kafka.common.TopicPartition
import org.apache.spark.streaming.kafka010.OffsetRange

class KafkaOffsetZK(zkServer:String) {
//  创建zookeeper连接客户端
  val zkClient = {
    val client = CuratorFrameworkFactory
//      创建加载对象
      .builder()
//      加载Server服务
      .connectString(zkServer)
//      连接次数和时间
      .retryPolicy(new ExponentialBackoffRetry(1000,3))
      .build()

    client.start()
    client
  }
//  创建ZK内部保存Offset路径
  val path_of_kafka_offset = "/kafka/offsets"

  /**
    * 获取消费者组下面地topic-offset
    * topics  topic集合
    * GROUP_ID   消费者组
    */
    def getFromOffset(topics:Array[String],groupName:String) ={
//      创建保存TopicPartition ->Offset Map
      var fromOffset :Map[TopicPartition,Long] =Map()
//    循环输出Topic
      for (topic<- topics){
//        读取zk中保存的offset,作为DStream起始位置，如果没有则创建该路径，从0开始
        val zkTopicPath = s"${path_of_kafka_offset}/${groupName}/${topic}"
//        检查路径是否存在
        checkZKPathExists(zkTopicPath)
//        获取topic的分区
        val partitions = zkClient.getChildren.forPath(zkTopicPath)
//        遍历分区
        import scala.collection.JavaConversions._
        for (p <- partitions) {
          //          获取每个分区中的offset
          val offsetData = zkClient.getData.forPath(s"${zkTopicPath}")
          println(offsetData.toString+"................")
//处理字节数组
          import scala.collection.JavaConversions._
        val offset = java.lang.Long.valueOf(new String(offsetData)).toLong
          //          将数据存储Map中，进行返回
          fromOffset +=(new TopicPartition(topic,Integer.parseInt(p)) ->offset)
        }
      }
      fromOffset
    }

  /**
    * 检查该路径是否存在
    */
  def checkZKPathExists(zkTopicPath: String) ={
//    如果路径为空，创建该路径
    if (zkClient.checkExists().forPath(zkTopicPath) ==null){
      zkClient.create().creatingParentsIfNeeded().forPath(zkTopicPath)
    }
  }
  /**
    *更新offset
    */
  def UpdateOffset(offsetRanges:Array[OffsetRange], groupName:String) ={
    for (o <- offsetRanges){
//      获取路径
      val zkPath = s"${path_of_kafka_offset}/${groupName}/${o.topic}/${o.partition}"
//    检测路径是否存在
      checkZKPathExists(zkPath)
//      向对应分区更新Offset或者第一次维护Offset
      zkClient.setData().forPath(zkPath,o.untilOffset.toString.getBytes())
    }
  }
}
