package day07

import org.apache.spark.{Accumulable, SparkConf, SparkContext}

/**
  * 累加器
  */
object Acc extends App {
  val conf: SparkConf = new SparkConf().setAppName("accumulator").setMaster("local")
  val sc = new SparkContext(conf)

  val numbers =sc.makeRDD(Array(1,2,3,4,5),2)
//  println(numbers.partitions.length)
//  为什么sum值通过计算还是0
//  因为foreach是没有返回值的，整个计算过程都是在executor完成的
//  foreach是在driver运行的，所以打印的就是0，foreach没法获取数据
//  var sum = 0
//  numbers.foreach(num=>{
//    sum+=num
//  })
//  println(sum)

}
