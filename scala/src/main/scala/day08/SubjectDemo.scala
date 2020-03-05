package day08

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import redis.clients.jedis.Tuple
/*
 * 统计学科每个时间段内的学科访问量Top2
*/
object SubjectDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SubjectDemo").setMaster("local")
    val sc = new SparkContext(conf)
    // 1.对数据进行切分
    val tuples =
      sc.textFile("D:\\千峰\\第二阶段\\学习内容\\数仓项目和spark\\day16-Spark-RDD解释\\Day03\\Day03\\dataSource\\subjectaccess\\access.txt").map(line => {
        val fields: Array[String] = line.split("\t")
        //取出url
        val url = fields(1)
        val time = fields(0)
        ((time,url), 1)
      })
    //将相同url进行聚合,得到了各个学科的访问量
    val sumed = tuples.reduceByKey(_+_)
    //从url中获取学科的字段 数据组成式 学科, url 统计数量
    val subjectAndUC= sumed.map(tup => {
      val url = tup._1._2 //用户url
      val time =tup._1._1  //用户时间
      val count = tup._2 // 统计的访问数量
      val subject = new URL(url).getHost //学科
      (time,(subject, count))
    })
    //按照学科和时间信息进行分组
    val grouped = subjectAndUC.groupBy(_._1)
    //对分组数据进行组内排序
    val sorted =
      grouped.mapValues(_.toList.sortBy(_._2).reverse)
    //取top3
    val res= sorted.mapValues(_.take(2))
    res.foreach(println)
//    println(res.collect.toList)
    sc.stop()
  }
}