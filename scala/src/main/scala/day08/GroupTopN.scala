package day08

import org.apache.spark.{SparkConf, SparkContext}

object GroupTopN {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("TopN").setMaster("local")
    val sc = new SparkContext(conf)
//    读取数据
    val lines =sc.textFile("D:\\score.txt")
//    切分数据
    val words =lines.map(x=>{
      val str =x.split(" ")
      (str(0),str(1).toInt)
    })
    val group =words.groupByKey()
    val tuples= group.map(t=>{
      val score = t._2.toList.sortWith(_ > _).take(2)
      (t._1,score)
    })
    tuples.foreach(println)
    sc.stop()
  }
}
