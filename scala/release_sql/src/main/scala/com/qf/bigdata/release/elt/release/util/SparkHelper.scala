package com.qf.bigdata.release.elt.release.util
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

import scala.collection.mutable.ArrayBuffer

object SparkHelper {

  /**
    * 读取数据表
    */
  def readTableData(spark: SparkSession, ods: String, cc: ArrayBuffer[String]) = {
    val tabDF =spark.read.table(ods)
      .selectExpr(cc:_*)
    tabDF
  }

  /**
    * 写入数据到hive
    * @param sourceDF
    * @param table
    * @param mode
    */
  def writeTableData(sourceDF: DataFrame, table: String, mode: SaveMode) ={
  //  写入对应的表
  sourceDF.write.mode(mode).insertInto(table)
}
  def createSpark(conf:SparkConf) ={
    val  spark = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate()
//    注意  这里有自定义函数在此加载
//    加载自定义函数
    spark
  }

  /**
    * 解析时间函数
    */
  def rangeDates(begin: String,end: String) ={
    val bdp_days = new ArrayBuffer[String]()
    try{
      val bdp_day_begin = DateUtil.dateFromat4String(begin)
      val bdp_day_end = DateUtil.dateFromat4String(end)
//    如果传入的参数一样，那么代表处理一天的数据
      if (begin.equals(end)){
        bdp_days.+=(bdp_day_begin)
      }else{
        var cday = bdp_day_begin
        while (cday!=bdp_day_end){
          bdp_days.+=(cday)
          val pday = DateUtil.dateFromat4StringDiff(cday,1)
          cday=pday
        }
      }
    }catch {
      case  e:Exception=>{
        e.printStackTrace()
      }
    }
    bdp_days
  }
}
