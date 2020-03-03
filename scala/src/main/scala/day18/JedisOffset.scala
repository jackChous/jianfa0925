package day18

import day16.hw.JedisConnectionPool
import org.apache.kafka.common.TopicPartition

/**
  * 获取Offset
  */
object JedisOffset {
  def apply(groupId:String)={
//    创建Map
    var fromOffset =Map[TopicPartition,Long]()
//    获取连接
    val jedis = JedisConnectionPool.getConnection()
//    查询出每个topic下面的Partition
    val topicPartition = jedis.hgetAll(groupId)

    import scala.collection.JavaConversions._
//    将map转换为List
    val topicPartitionOffset = topicPartition.toList
    //  循环输出
    for (topicPL <- topicPartitionOffset){
//     切分topicPartition
      val split = topicPL._1.split("-")
//      Offset
      topicPL._2
//      将数据存储Map
      fromOffset +=(new TopicPartition(split(0),split(1).toInt)->topicPL._2.toLong)
    }
    fromOffset
  }
}
