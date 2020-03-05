package day12

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

object UserLog extends App {
  private val conf: SparkConf = new SparkConf().setAppName("spark").setMaster("local")
  private val sc = new SparkContext(conf)
  private val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()
  val arr =Array(
    "2019-11-12,55.5,1122",
    "2019-11-11,88.5,1133",
    "2019-11-11,70.5,1144",
    "2019-11-12,60.5,1155",
    "2019-11-12,55.5,1166",
    "2019-11-11,55.5,1177",
    "2019-11-12,1188",
    "2019-11-11,1199",
    "2019-11-12,55.5,1122"
  )
    val rdd = sc.makeRDD(arr).map(_.split(","))
//  需求：统计每天的销售额，如果当天没有销售额，需要过滤不符合的字段
  import org.apache.spark.sql.functions._
  import spark.implicits._
  private val tuple = rdd.filter(log=>if(log.length==3)true else
  false).map(x=>(x(0),x(1).trim().toDouble))
//private val tuple = rdd.filter(log=>if(log.length==3)true else
//  false).map(x=>user(x(0),x(1).trim().toDouble))
  private val value: RDD[(String, Double)] = tuple.reduceByKey(_+_)
value.foreach(println)
//  private val df: DataFrame = tuple.toDF("date","memory")
//  df.createTempView("t")
//  private val frame: DataFrame = df.groupBy("date").agg(sum("memory"))
//spark.sql("select date,sum(memory) sum from t group by date").show()
//  frame.show()
}

case class user(date:String,menory:Double)
