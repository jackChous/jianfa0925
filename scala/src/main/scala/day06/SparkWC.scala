package day06

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Spark-WC
  */
object SparkWC extends App {
  //首先第一步创建SparkConf配置文件对象
//  setAppName：设置程序运行名称
//  setMaster：设置运行模式(local)
//  local:代表用一个线程来完成程序运行；local[4]：代表用4个core来完成运行任务
//  local[*]：用所有空闲的线程来运行任务
  val conf: SparkConf = new SparkConf().setAppName("SparkWC")
//  如果是提交集群运行的话，那么需要注释掉当前的setMaster
//  .setMaster("local")
//创建SparkContext上下文对象，也就是集群的运行入口
  val sc = new SparkContext(conf)
//  读取数据文件
  val lines: RDD[String] = sc.textFile("D:\\InstallConfig.ini")
//  进行单词统计，首先进行切分
  val words: RDD[String] = lines.flatMap(_.split(" "))
//  进行单词统计，变成元组
  val tuples: RDD[(String, Int)] = words.map((_, 1))
//  统计单词,进行分组
//  val grouped: RDD[(String, Iterable[(String, Int)])] = tuples.groupBy(_._1)
  //  计算求和
//  private val sum: RDD[(String, Int)] = grouped.mapValues(_.size)
//  打印输出

//  单词统计，直接聚合
  val sum: RDD[(String, Int)] = tuples.reduceByKey(_+_)
//  sum.foreach(println)
  sum.saveAsTextFile("hdfs://hadoop01:9000/sparkwc")
  sc.stop()
}
