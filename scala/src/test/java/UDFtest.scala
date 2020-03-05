import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

object UDFtest extends App {
  private val spark: SparkSession = SparkSession.builder().appName("udf").master("local").getOrCreate()

  private val rdd: RDD[String] = spark.sparkContext.makeRDD(Array("zhangsan","lisi","wangwu","zhaoliu"))
//  转换成DF
  private val rddUdf: RDD[uDF] = rdd.map(t=>uDF(t))
  import spark.implicits._
  private val df: DataFrame = rddUdf.toDF()
//  注册临时视图
  df.createTempView("udf")
//  定义和注册自定义函数
//  定义函数
  val u=(str:String) =>{str.length}
//  注册函数
//  spark.udf.register("strLen",u)
  spark.udf.register("strLen",udf2Len _)
//  使用函数
  spark.sql("select str,strLen(str) from udf").show()

  def udf2Len(str:String):Int={
    str.length
  }
}


case class uDF(str:String)