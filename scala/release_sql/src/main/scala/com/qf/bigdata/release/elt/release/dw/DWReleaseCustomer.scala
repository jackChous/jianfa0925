package com.qf.bigdata.release.elt.release.dw

import com.qf.bigdata.release.constan.ReleaseConstant
import com.qf.bigdata.release.elt.release.util.SparkHelper
import com.qf.bigdata.release.enums.ReleaseStatusEnum
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
  * dw 投放目标客户主题
  */
class DWReleaseCustomer {

}

object DWReleaseCustomer{
  /**
    * 目标客户
    * status = 01
    */
  def handleReleaseJob(spark:SparkSession,appNmae:String,bdp_day:String): Unit ={
    try{
//      导入相关的包
      import spark.implicits._
      import  org.apache.spark.sql.functions._
      //可以设置缓存级别
      val storageLevel = ReleaseConstant.DEF_STORAGE_LEVEL
//      获取日志字段
      val customerColumns = DWReleaseColumnsHepler.selectDWCustomerColumns()
//      通过主题需求，进行判断处理
//      判断是否当天数据
      val customerReleaseConition =
      col(s"${ReleaseConstant.DEF_PARTITION}") ===lit(bdp_day) and col(s"${ReleaseConstant.COL_RELEASE_SESSION_STATUS}")===lit(ReleaseStatusEnum.CUSTOMER.getCode)
//     按照表查询数据
      val customerReleaseDF=SparkHelper.readTableData(
        spark,ReleaseConstant.ODS_RELEASE_SESSION,customerColumns)
//     添加条件
      .where(customerReleaseConition)
//      重分区
        .repartition(ReleaseConstant.DEF_SOURCE_PARTITIONS)
//      做成缓冲表
        .persist(storageLevel)
//      查询数据
      customerReleaseDF.show(10,false)
//      目标客户存储数据
//      SparkHelper.writeTableData(customerReleaseDF,ReleaseConstant.DW_RELEASE_CUSTOMER,ReleaseConstant.SAVE_MODE)
    }catch {
      case e:Exception=>{
//        打印异常
        e.printStackTrace()
      }
    }
  }

  /**
    * 执行方法
    */
  def handleJobs(appName:String,bdp_day_degin:String,bdp_day_end:String) ={
    var spark:SparkSession =null
    try{
      val conf = new SparkConf()
        .setAppName(appName)
        .setMaster("local[*]")
//      spark上下文
      val spark  =SparkHelper.createSpark(conf)
//      解析参数
      val timeRange = SparkHelper.rangeDates(bdp_day_degin,bdp_day_end)
      for (bdp_day <- timeRange.reverse){
//        有多少天就处理多少次
        handleReleaseJob(spark,appName,bdp_day)
      }
    }catch {
      case e:Exception=>{
        e.printStackTrace()
      }
    }
  }

  def main(args: Array[String]): Unit = {

    // 测试启动
    handleJobs("release","20191020","20191021")
  }
}