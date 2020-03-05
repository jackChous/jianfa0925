package day09

import org.apache.spark.broadcast.Broadcast
import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.hadoop.mapred.TextInputFormat
import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

import scala.collection.mutable

object CountByHours {
  def main(args: Array[String]): Unit = {

    //1、启动spark上下文、读取文件
    val conf = new SparkConf().setAppName("sougo count by hours").setMaster("local")
    val sc = new SparkContext(conf)
//    var orgRdd = sc.textFile("D:\\千峰\\第二阶段\\学习内容\\数仓项目和spark\\day19-IP、搜狗案例\\SogouQ.txt")
    val orgRdd = sc.hadoopFile("D:\\千峰\\第二阶段\\学习内容\\数仓项目和spark\\day19-IP、搜狗案例\\SogouQ.txt",
      classOf[TextInputFormat], classOf[LongWritable], classOf[Text]).map {
      pair => new String(pair._2.getBytes, 0, pair._2.getLength, "GBK")
    }

    val words=orgRdd.map(t=>{
      val str = t.split("\\s")
      val types=str(4)
      val hours =str(0).split(":")(0)
      ((hours,types),1)
    })
//将数据聚合
    val reduce = words.reduceByKey(_+_)


    val doc = sc.textFile("D:\\千峰\\第二阶段\\学习内容\\数仓项目和spark\\day19-IP、搜狗案例\\ID.txt")
    val file = doc.map(t => {
      val str = t.split(" ")
      (str(0), str(1))
    })
//    字典数据
    val map = file.collectAsMap()
    val value = sc.broadcast(map)
    //    广播数据
    val broad =value
//去除对应的类别
    val hourTypeCount = words.map(t => {
      val types = broad.value.getOrElse(t._1._2, "其它")
      (t._1._1, (types, t._2))
    })

    val arr = hourTypeCount.map(_._1).distinct().collect()
    val sogouPar = new SougouPartition(arr)


    val groupSogou = hourTypeCount.groupByKey(sogouPar)



  }

  class SougouPartition(arr:Array[String]) extends Partitioner{
//    创建map集合来存储分区号和学科
    val map =new mutable.HashMap[String,Int]()
    override def numPartitions: Int = arr.size +1

    override def getPartition(key: Any): Int = ???
  }
}