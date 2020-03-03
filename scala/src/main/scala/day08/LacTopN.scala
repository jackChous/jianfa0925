package day08

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 基站统计
  * 求用户经过的所有基站所停留的时间最长的Top2
  */
object LacTopN {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("TopN").setMaster("local")
    val sc = new SparkContext(conf)
//    读取数据
    val lines = sc.textFile("D:\\千峰\\第二阶段\\学习内容\\数仓项目和spark\\day18-Spark任务提交流程（重点）\\dataSource\\lacduration\\log\\19735E1C66.log")
//    切分数据
    val phoneAndLacAndTime =  lines.map(t=>{
      val str =t.split(",")
      val phone = str(0) //手机号
      val time = str(1).toLong //时间戳
      val lac =str(2)  //基站
      val eventType = str(3).toInt //状态
      val time_long = if(eventType ==1) -time else time
      ((phone,lac),time_long)
    })
//    用户在相同基站停留的时间Sum
    val sumPhoneAndLacAndTime = phoneAndLacAndTime.reduceByKey(_+_)

//    接下来读取基站信息
    val lacInfo=sc.textFile("D:\\千峰\\第二阶段\\学习内容\\数仓项目和spark\\day18-Spark任务提交流程（重点）\\dataSource\\lacduration\\lac_info.txt")
//    切分基站信息
    val lacXY = lacInfo.map(t=>{
      val str = t.split(",")
//      基站ID
      val lac = str(0)
//      经纬度
      (lac,(str(1),str(2)))
    })
//    将用户数据修改
    val lacPhoneTime = phoneAndLacAndTime.map(x=>{
      (x._1._2,(x._1._1,x._2))
    })
//    将基站数据jion用户数据
    val lacJoinUser = lacXY.join(lacPhoneTime)
//    整理数据
    val phoneTimeXY = lacJoinUser.map(t=>{
      val lac = t._1  //基站
      val phone = t._2._2._1 //手机号
      val xy = t._2._1 //坐标
      val time = t._2._2._2  //时长
      (phone,time,xy)
    })
    val grouped: RDD[(String, Iterable[(String, Long, (String, String))])]
    = phoneTimeXY.groupBy(_._1)

    val value: RDD[(String, List[(String, Long, (String, String))])]
    = grouped.mapValues(_.toList.sortBy(_._2).reverse.take(2))
    //    按照时长进行统计
    val tuples = value
//    循环输出
tuples.foreach(println)
    sc.stop()
  }
}
