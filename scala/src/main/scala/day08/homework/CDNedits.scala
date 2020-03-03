package day08.homework

import org.apache.spark.{SparkConf, SparkContext}

import scala.actors.migration.pattern
import scala.util.matching.Regex

class CDNedits{

}

object CDNedits {
  //[15/Feb/2017:11:17:13 +0800]  匹配 2017:11 按每小时播放量统计
  val  timePattern=".*(2017):([0-9]{2}):[0-9]{2}:[0-9]{2}.*".r
  //匹配 http 响应码和请求数据大小
  val httpSizePattern=".*\\s(200|206|304)\\s([0-9]+)\\s.*".r


  def main(args: Array[String]): Unit = {
    //  IPindependentOfnumber()
    TimePattern
//    IPindependentOfVideo
  }

  def IPindependentOfnumber() = {
    val conf = new SparkConf().setAppName("UVPV").setMaster("local")
    val sc = new SparkContext(conf)
    //        读取数据
    val lines = sc.textFile("D:\\千峰\\第二阶段\\学习内容\\数仓项目和spark\\" +
      "day18-Spark任务提交流程（重点）\\作业\\案例\\cdn.txt")
    //匹配IP地址

    val IPPattern = "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))".r
    val IPadress = lines.flatMap(x => IPPattern findFirstIn (x))
    val IPpair = IPadress.map(x => {
      (x, 1)
    })
    val IPreduce = IPpair.reduceByKey(_ + _)
    val IPsorted = IPreduce.sortBy(_._2, false)
    IPsorted.foreach(println)

    sc.stop()
  }

  def IPindependentOfVideo() = {
    val conf = new SparkConf().setAppName("UVPV").setMaster("local")
    val sc = new SparkContext(conf)
    //        读取数据
    val lines = sc.textFile("D:\\千峰\\第二阶段\\学习内容\\数仓项目和spark\\" +
      "day18-Spark任务提交流程（重点）\\作业\\案例\\cdn.txt")

    val IPPattern = "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))".r


    //匹配文件名
    val  fileNamePattern="([0-9]+).mp4".r
    def getFileNameAndIp(line:String)={
      (fileNamePattern.findFirstIn(line).mkString,IPPattern.findFirstIn(line).mkString)
    }
    //2.统计每个视频独立IP数
    lines.filter(x=>x.matches(".*([0-9]+)\\.mp4.*")).map(x=>getFileNameAndIp(x)).groupByKey().map(x=>(x._1,x._2.toList.distinct)).
      sortBy(_._2.size,false).take(10).foreach(x=>println("视频："+x._1+" 独立IP数:"+x._2.size))
  }

    def  TimePattern(): Unit ={
      val conf = new SparkConf().setAppName("UVPV").setMaster("local")
      val sc = new SparkContext(conf)

      val lines = sc.textFile("D:\\千峰\\第二阶段\\学习内容\\数仓项目和spark\\" +
        "day18-Spark任务提交流程（重点）\\作业\\案例\\cdn.txt")

      //[15/Feb/2017:11:17:13 +0800]  匹配 2017:11 按每小时播放量统计
      val  timePattern=".*(2017):([0-9]{2}):[0-9]{2}:[0-9]{2}.*".r
      //匹配 http 响应码和请求数据大小
      val httpSizePattern=".*\\s(200|206|304)\\s([0-9]+)\\s.*".r

      def  isMatch(pattern:Regex,str:String)= {
        str match {
          case pattern(_*) => true
          case _ => false
        }
      }

      //3.统计一天中每个小时间的流量
      lines.filter(x=>isMatch(httpSizePattern,x)).filter(x=>isMatch(timePattern,x)).map(x=>getTimeAndSize(x)).groupByKey()
        .map(x=>(x._1,x._2.sum)).sortByKey().foreach(x=>println(x._1+"时 CDN流量="+x._2/(1024*1024*1024)+"G"))


    }
  def getTimeAndSize(line:String)={
    var res=("",0L)
    try{
      val  httpSizePattern(code,size)=line
      val  timePattern(year,hour)=line
      res=(hour,size.toLong)
    }catch {
      case ex:Exception  => ex.printStackTrace()
    }
    res
  }

  }



