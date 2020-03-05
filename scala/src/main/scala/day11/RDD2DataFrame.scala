package day11

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}


/**
  * RDD转换dataframe
  * 反射方式
  */
object RDD2DataFrame {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RDD2DF").setMaster("local")
    val sc = new SparkContext(conf)
    val SqlContext = SparkSession.builder().config(conf).getOrCreate()
    val lines = sc.textFile("D:\\wc.txt")

    var stu:RDD[students]= lines.map(t=>{
      val str = t.split(",")
      students(str(0).toInt,str(1),str(2).toInt)
    })
    //转换成DF  导入隐式转换
    import SqlContext.implicits._
    val df:DataFrame = stu.toDF()

    //  df.printSchema()
    df.createOrReplaceTempView("stu")
    val df2 = SqlContext.sql("select * from stu where age <18")
    df2.show()
  }
}

//定义一个样例类
case class students(id:Int,name:String,age:Int)