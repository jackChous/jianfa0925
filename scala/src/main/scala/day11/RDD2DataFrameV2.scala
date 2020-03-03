package day11


import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, Row, SQLContext, SparkSession}

/**
  * 编程接口方式创建DF
  */
object RDD2DataFrameV2 {
  val conf = new SparkConf().setAppName("RDD2DF").setMaster("local")
  val sc = new SparkContext(conf)
  val SqlContext =SparkSession.builder().config(conf).getOrCreate()
//  创建RDD
  val lines = sc.textFile("D:\\wc.txt")

  var rddRow:RDD[Row]= lines.map(t=>{
    val str = t.split(",")
    Row(str(0).toInt,str(1),str(2).toInt)
  })
//  构建元数据的StructType
//  def apply(fields:Seq[StructField])
  val struc = StructType(Array(
    StructField("id",IntegerType),
    StructField("name",StringType),
    StructField("age",IntegerType)
  )
  )
//  创建DF
  private val df: DataFrame = SqlContext.createDataFrame(struc)
//  创建临时表
  df.createTempView("stu")

}
