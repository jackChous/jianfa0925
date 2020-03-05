package day12

import java.util.Properties

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

object DFandUV extends App {
  val conf: SparkConf = new SparkConf().setAppName("df").setMaster("local")
  private val sc = new SparkContext(conf)
  private val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()
  private val df: DataFrame = spark.read.json("D:\\千峰\\第二阶段\\学习内容\\数仓项目和spark\\day29-redis维护偏移量\\机试题\\JsonTest02.json")

  import org.apache.spark.sql.functions._
  import spark.implicits._


  df.groupBy("terminal").agg(countDistinct("name") as("uv")).show()






}










