package day11.hw

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, SQLContext, SparkSession}

object DFtest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local")
    val SqlContext =SparkSession.builder().config(conf).getOrCreate()

    val df1 = SqlContext.read.
      option("header", true).
      option("inferSchema", true).
      csv("D:\\千峰\\第二阶段\\学习内容\\数仓项目和spark\\day21-Dataframe\\data.csv")
import SqlContext.implicits._
import org.apache.spark.sql.functions._

//  val df1 =df.toDF()
//    df1.printSchema()
    //    println(df1.count())
    //      df1.union(df1).show()
//          df1.show(20)
    //        df1.toJSON.show(false)
    //        df1.toJSON.show(10,false)

    //    val c1 = df1.collect()
    //    println(c1.toBuffer)

    //    val c2 = df1.collectAsList()
    //println(c2)

    //     val h1 = df1.head()
    //    val f1 = df1.first()
    //println(h1)
    //    println(f1)

    //    val h2 = df1.head(3)
    //val f2 = df1.take(3)
    //println(h2.toBuffer)
    //    println(f2.toBuffer)

    //    df1.takeAsList(2)

//          df1.map(row=>row.getAs[Int](0)).show()
//        case class Peoples(age:Int,names:String)
//        val sql1=Seq(Peoples(30,"Tom,Andy,Jack"),Peoples(20,"Pand,Gate,Sundy"))
//        val ds1 =SqlContext.createDataset(sql1)
//        val ds2 = ds1.map(x =>(x.age+1, x.names)).show()

//    df1.filter("EMPNO>7900").show()
//      df1.describe().show()
//df1.select("EMPNO","ENAME").show()
//df1.selectExpr("cast(empno as string)").printSchema()
//df1.groupBy("Job").sum("sal").show()
//df1.select(current_date).show

//    row进行取值时要注意大小写
//  df1.map(row=>row.getAs[Int](0)).show()
    //  df1.map(row=>row.getAs[Int]("ENAME")).show()
  df1.select($"ename").show()
//  df1.select(current_date()).show()


  }
}
