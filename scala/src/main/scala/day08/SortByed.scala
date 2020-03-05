package day08

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 定义比较器
  */
class SortByed(val first:Int,val second:Int) extends Ordered[SortByed] with Serializable {
  override def compare(that: SortByed): Int = {
    if (this.first -that.first !=0){
      this.first -that.first
    }else{
      this.second -that.second
    }
  }
}


object SortByed{
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("sort").setMaster("local")
    val sc = new SparkContext(conf)
//    读取本地文件
    val lines =sc.textFile("D:sort.txt")
//    处理数据

    val words = lines.map(t=>{
      val str =t.split(" ")
      (new SortByed(str(0).toInt,str(1).toInt),t)
    })
    val sorted =words.sortByKey()
    sorted.foreach(println)
  }
}