package day12

import org.apache.spark.sql.SparkSession

object SparkSessionCreate {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("SparkSession")
      .master("local")
      .getOrCreate()


//    操作Hive
    SparkSession.builder()
      .appName("ss")
      .master("local")
      .enableHiveSupport()
      .getOrCreate()

  }
}
