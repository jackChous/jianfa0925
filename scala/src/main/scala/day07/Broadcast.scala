package day07

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 广播变量（Broadcast）
  * 释放广播变量unpersist
  * 使用场景：比如在本地有字典文件，需要读取并且不是太大，那么此时可以使用广播变量将其广播到
  * executor内存中，供给每个task进行使用，减少数据冗余性，提高效率
  */
object Broadcast extends App {
  val conf =new SparkConf().setAppName("Broadcast").setMaster("local")
  val sc = new SparkContext(conf)
  val rdd =sc.makeRDD(Array(1,2,3))
  val factor = 10
//  将变量广播
  val broad: Broadcast[Int] = sc.broadcast(factor)
  private val value: Broadcast[Array[Int]] = sc.broadcast(rdd.collect())
  val words =rdd.map(_ * broad.value)
  words.foreach(println)
}
