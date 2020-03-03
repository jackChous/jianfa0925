package day07

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.util.AccumulatorV2

/**
  * 自定义累加器
  * 第一个泛型参数表示输入值类型
  * 第二个泛型参数表示输出值类型
  */

class MyAccumulator extends AccumulatorV2[Int,Int]{
//  创建输出值的变量
  private var sum:Int = _

//  检查当前值是否为空
  override def isZero: Boolean = sum ==0

//拷贝新的累加器
  override def copy(): AccumulatorV2[Int,Int] ={
//    需要创建自定义的累加器类
    val myAccumulator = new MyAccumulator
//    需要将当前输入进来的数据进行迭代计算
    myAccumulator.sum=this.sum
    myAccumulator
  }

//重置一个累加器，说白了就是数据清空
  override def reset(): Unit = sum = 0
//每个分区内部进行添加（分区累加）
  override def add(v: Int): Unit = {
    sum+= v
  }
//合并每个分区的输出值
  override def merge(other: AccumulatorV2[Int, Int]): Unit = {
//    将每个分区中的数据进行汇总
    sum+=other.value
  }

//  输出结果
  override def value: Int = sum
}

//测试输出
object MyAccumulator{
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("accumulator").setMaster("local")
    val sc = new SparkContext(conf)

    val numbers =sc.makeRDD(Array(1,2,3,4,5,6),2)
//    实例化
    val accumulator = new MyAccumulator
//    注册累加器
    sc.register(accumulator,"acc")
//    使用累加器
    numbers.foreach(x=>accumulator.add(x))
    println(accumulator.value)
  }
}