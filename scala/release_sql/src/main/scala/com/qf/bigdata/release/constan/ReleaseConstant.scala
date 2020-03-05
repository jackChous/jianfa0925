package com.qf.bigdata.release.constan

import org.apache.spark.sql.SaveMode
import org.apache.spark.storage.StorageLevel

/**
  * 常量
  */
object ReleaseConstant {
  val COL_RELEASE_TOTAL_COUNT = "total_count"
  val COL_RELEASE_USER_COUNT ="user_count"

  val COL_RELEASE_DEVICE_NUM = "device_num"

  val COL_RELEASE_AREA_CODE = "area_code"

  val COL_RELEASE_GENDER = "gender"

  val COL_RELEASE_AGE_RANGE = "age_range"

  val COL_RELEASE_DEVICE_TYPE = "device_type"

  val COL_RELEASE_CHANNELS = "channels"

  val COL_RELEASE_SOURCES = "sources"

  val SAVE_MODE =SaveMode.Overwrite

  val DW_RELEASE_CUSTOMER = "dw_release.dw_release_customer"

  val DEF_SOURCE_PARTITIONS =4

  val ODS_RELEASE_SESSION: String = "ods_release.ods_01_release_session"

  val COL_RELEASE_SESSION_STATUS:String = "release_status"

  val DEF_PARTITION: String ="bdp_day"

  val  DEF_STORAGE_LEVEL =StorageLevel.MEMORY_AND_DISK

}
