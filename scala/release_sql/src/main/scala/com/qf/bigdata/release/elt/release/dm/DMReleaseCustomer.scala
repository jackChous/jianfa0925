package com.qf.bigdata.release.elt.release.dm

import com.qf.bigdata.release.constan.ReleaseConstant
import com.qf.bigdata.release.elt.release.util.SparkHelper
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Column

object DMReleaseCustomer {
def handleReleaseJob(spark:SparkSession,appName:String,bdp_day:String)={
//  缓存级别
  import spark.implicits._
  import org.apache.spark.sql.functions._
  val storageLevel = ReleaseConstant.DEF_STORAGE_LEVEL
//  获取字段
  val customerColumns = DMReleaseColumnsHelper.selectDWReleaseCustomerColumns()
//查询主题表数据
  val customerCol = DMReleaseColumnsHelper.selectDWReleaseCustomerColumns()
  val customerReleaseConition =
    col(s"${ReleaseConstant.DEF_PARTITION}")===lit(bdp_day)
  val customerReleaseDF = SparkHelper
    .readTableData(spark,ReleaseConstant.DW_RELEASE_CUSTOMER,customerCol)
    .where(customerReleaseConition)


  //  设置条件语句
  val customerGroupColumns = Seq[Column](
    $"${ReleaseConstant.COL_RELEASE_SOURCES}",
    $"${ReleaseConstant.COL_RELEASE_CHANNELS}",
    $"${ReleaseConstant.COL_RELEASE_DEVICE_TYPE}",
    $"${ReleaseConstant.COL_RELEASE_AGE_RANGE}",
    $"${ReleaseConstant.COL_RELEASE_GENDER}",
    $"${ReleaseConstant.COL_RELEASE_AREA_CODE}"
  )
    val DF =customerReleaseDF.groupBy(customerGroupColumns:_*)
    .agg(countDistinct(col(s"${ReleaseConstant.COL_RELEASE_DEVICE_NUM}"))
        .alias(s"${ReleaseConstant.COL_RELEASE_USER_COUNT}"),
    count(col(s"${ReleaseConstant.COL_RELEASE_DEVICE_NUM}"))
        .alias(s"${ReleaseConstant.COL_RELEASE_TOTAL_COUNT}"))
      .selectExpr(customerColumns:_*)



    )

}
}
