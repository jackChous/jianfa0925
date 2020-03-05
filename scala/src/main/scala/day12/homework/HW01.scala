package day12.homework


import org.apache.spark.sql.{DataFrame, SparkSession}

object HW01 extends App {
  private val spark: SparkSession = SparkSession.builder().appName("hw").master("local").getOrCreate()
  private val a: DataFrame = spark.read.json("D:\\千峰\\第二阶段\\学习内容\\数仓项目和spark\\day22-内置函数\\作业\\作业\\a.json")
  private val b: DataFrame = spark.read.json("D:\\千峰\\第二阶段\\学习内容\\数仓项目和spark\\day22-内置函数\\作业\\作业\\b.json")

  import spark.implicits._
  import org.apache.spark.sql.functions._

  a.join(b,a("depId")===b("id")).filter("age>20").select(a("name"),a("age"),a("gender")
    ,a("salary"),a("depId"),b("name") as("depName")).show()

  b.join(a,a("depId")===b("id")).groupBy(b("name"),a("gender")).agg(avg("salary"),avg("age")).show()










}
