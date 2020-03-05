package day09

import java.sql.{Connection, DriverManager, SQLException}
import java.util

object JDBCTest {
  val driver = "com.mysql.jdbc.Driver"
  val dbUrl = "jdbc:mysql://localhost:3306/spark-1"
  val userName = "root"
  val password = "123456"

  var pool:util.LinkedList[Connection] = null
  var connection:Connection = null

  private def getOneConnection = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(
        JDBCTest.dbUrl,JDBCTest.userName,JDBCTest.password)
    } catch {
      case e@(_: ClassNotFoundException | _: SQLException) =>
        e.printStackTrace()
    }
    connection
  }

  def getConnection: Connection = {
    if (pool == null) {
      pool = new util.LinkedList[Connection]
      var i = 0
      while ( i < 2) {
        pool.add(getOneConnection)
        i += 1
      }
    }
    if (pool.size <= 0) pool.add(getOneConnection)

      pool.remove
  }

  def close(connection: Connection): Unit = {
    pool.add(connection)
  }
}