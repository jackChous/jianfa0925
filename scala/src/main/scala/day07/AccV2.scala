package day07

import org.apache.spark.util.{DoubleAccumulator, LongAccumulator}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 新版累加器
  */
object AccV2 extends App {
  val conf: SparkConf = new SparkConf().setAppName("accumulator").setMaster("local")
  val sc = new SparkContext(conf)

  val numbers =sc.makeRDD(Array(1,2,3,4,5),2)
  val numbers2 = sc.makeRDD(Array(1.1,2.1,3.1,4.1,5.1),2)
//  创建并且注册一个long Accumulator 从0开始，用add累加
  def longAccumulator(name:String):LongAccumulator={
    val acc = new LongAccumulator
    sc.register(acc,"zhangsan")
    acc
  }
//  累加操作
  val acc1: LongAccumulator = longAccumulator("zhangsan")
  numbers.foreach(x=>acc1.add(x))
  println(acc1.value)


  def doubleAccumulator(name:String):DoubleAccumulator={
  val acc = new DoubleAccumulator
    sc.register(acc,name)
    acc
  }
  val acc2=doubleAccumulator("lisi")
  numbers2.foreach(x=>acc2.add(x))
  println(acc2.value)
}
