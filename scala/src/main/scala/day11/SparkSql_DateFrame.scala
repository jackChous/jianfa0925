package day11

import org.apache.spark.sql.{DataFrame, SQLContext, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * DataFrame的基本使用
  */

object SparkSql_DateFrame {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("spark-sql").setMaster("local")
    val sc = new SparkContext(conf)
    val sQLContext = SparkSession.builder().config(conf).getOrCreate()
//    val wc = sQLContext.read.text("D:\\InstallConfig.ini")
//    查看内容
//    wc.show()

//    读取json格式数据
    val df = sQLContext.read.json("D:\\千峰\\第二阶段\\rating.json")

//    查看数据   如果不传人参数，默认显示20条数据，相当于select * from Table
//    df.show()
//    df.show(20,false)
//    获取元数据信息
    df.printSchema()     //--movie: string (nullable = true) ....

//    跟show 比较类似，但是返回的是Array数组
//    println(df.collect().toBuffer)

//    describe一次性统计传入参数的平均值，差值，最大值最小值，count值
//    df.describe("uid").show()

//    条件判断   filter 用于数据过滤 ，where用于条件性查询
//df.filter("uid =1 or rate =xxx").show()
//    df.where("uid =1 or rate =xxx").show()

//    按条件查询
//    df.select(df.col("uid")).show()

//    排序
//    df.sort(-df.col("uid")).show()

//    分组
//    df.groupBy("uid")

//    过滤某一列的值
//    df.filter($"uid" > 21).show()

//    分组聚合
//    df.groupBy("uid").count().show()


  }
}
