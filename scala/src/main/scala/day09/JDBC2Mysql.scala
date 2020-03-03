package day09

import java.sql.{Date, SQLException}

import org.apache.spark.{SparkConf, SparkContext}

object JDBC2Mysql {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("jdbc").setMaster("local")
    val sc = new SparkContext(conf)

    val reduce = sc.textFile("D:\\a.txt").map((_,1)).reduceByKey(_+_)
    // 如果调用JDBC连接的话，那么你需要使用每个分区创建一个Connection连接
    reduce.foreachPartition(f=>{
      // 将数据存储到MySQL数据库
      val connection = JDBCTest.getConnection
      // 加载SQL语句
      val pstmt = connection
        .prepareStatement("insert into spark01(name,age,date) values(?,?,?)")
      // 写入数据
      try {
        f.foreach(t=>{
          pstmt.setString(1,t._1)
          pstmt.setInt(2,t._2)
          pstmt.setDate(3,new Date(System.currentTimeMillis()))
          val i = pstmt.executeUpdate()
          if (i>0) println("写入成功") else println("写入失败")
        })
      }catch {
        case e:SQLException => println(e.getMessage)
      }finally {
        // 还链接
        JDBCTest.close(connection)
      }
    })
  }
}
