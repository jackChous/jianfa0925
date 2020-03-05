package day12.homework

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SQLContext, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}


object UVdemo extends App {
  private val conf: SparkConf = new SparkConf().setAppName("test").setMaster("local")
  private val sc = new SparkContext(conf)
  private val spark = SparkSession.builder().config(conf).getOrCreate()

  import spark.implicits._
  import org.apache.spark.sql.functions._

  val queryFilter = Map(
    "city"-> List("beijing"),
    "platform"->List("android"),
    "version" -> List("1.0")
  )

  private val queryBroadCast= sc.broadcast(queryFilter)

  private val rdd1: RDD[String] = sc.textFile("D:\\千峰\\第二阶段\\学习内容\\数仓项目和spark\\day22-内置函数\\searchLog.txt")

  private val filterRDD: RDD[String] = rdd1.filter(log => {
    val values = queryBroadCast.value
    val citys = values.get("city").get
    val platforms = values.get("platform").get
    val versions = values.get("version").get
    val city = log.split(",")(3)
    val platform = log.split(",")(4)
    val version = log.split(",")(5)
    var flag = true
    if (!citys.contains(city)) {
      flag = false
    }
    if (!platforms.contains(platform)) {
      flag = false
    }
    if (!versions.contains(version)) {
      flag = false
    }

    flag
  })

  private val rdd2= filterRDD.map(row => {
    (row.split(",")(0) + "_" + row.split(",")(2),row.split(",")(1))
  })

  private val df1 = rdd2.toDF("dateKeyWord","Users")
  private val df2 = df1.groupBy("dateKeyWord").agg(countDistinct("Users") as("counts"))

  val schemas= Seq("date", "keyword", "uv")

  private val datekeywordUV: DataFrame = df2.map(row => {
    val date = row.get(0).toString.split("_")(0)
    val word = row.get(0).toString.split("_")(1)
    val counts = row.get(1).toString.toInt
    (date, word, counts)
  }).toDF(schemas: _*)

  datekeywordUV.createOrReplaceTempView("t")

  spark.sql(""
    + "select date," +
    "         keyword," +
    "          uv " +
    "  from  " +
    " (select " +
    "       date," +
    "       keyword," +
    "       uv," +
    "       row_number() over(partition by date order by uv desc ) rn " +
    " from t) t1").show()

sc.stop()
  }





