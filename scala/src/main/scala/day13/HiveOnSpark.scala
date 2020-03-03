package day13

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object HiveOnSpark extends App {
  private val spark: SparkSession = SparkSession.builder().appName("hiveOnSpark")
    .master("local")
    .config("hive.merge.mapfiles", true)
    .config("hive.merge.mapredfiles", true)
    .enableHiveSupport()
    .getOrCreate()

//  查询hive库
//  spark.sql("show databases").show()


//  查询本地文件内容
//private val rdd1: RDD[String] = spark.sparkContext.textFile("file:///D:\\千峰\\第二阶段\\学习内容\\数仓项目和spark\\day21-Dataframe\\userlog.json")
//  rdd1.foreach(println)
  import spark.implicits._




  //  注册函数
  spark.udf.register("Uscore",Uscore _)
  spark.udf.register("Ulevel",Ulevel _)

  def Uscore(n:String):Int={
    var sums:Int = 0
    if (n == "01"){
      sums+=1
    }else if (n =="02"){
      sums+=2
    }else if (n =="03"){
      sums+=2
    }else if (n =="04"){
      sums+=3
    }else if (n =="05"){
      sums+=5
    }else{
      sums
    }
  sums
  }

  def Ulevel(uscore:Int):String={
    var n:String =""
    if (uscore >=1000 && uscore <3000){
      n="银牌"
    }else if (uscore >=3000 && uscore <10000){
      n="金牌"
    }else if (uscore >=10000){
      n="钻石"
    }else{
      n="普通"
    }
    n
  }


  spark.sql(
    """
      |select
      |users,
      |gender,
      |areacode,
      |uscore,
      |Ulevel(uscore) as ulevel
      |from
      |(select
      |a.users users,
      |a.gender gender,
      |a.areacode areacode,
      |sum(Uscore(b.action)) as uscore
      |from
      |ods_test.ods_02_user a join ods_test.ods_user_action_log b
      |on a.users = b.users
      |group by
      |a.users,
      |a.gender,
      |a.areacode ) t

    """.stripMargin).show(false)
}
//case class uDF(str:String)