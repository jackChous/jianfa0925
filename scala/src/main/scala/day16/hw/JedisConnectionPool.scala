package day16.hw

import java.util
import redis.clients.jedis._



object JedisConnectionPool{

  val config = new JedisPoolConfig()
  //最大连接数,
  config.setMaxTotal(20)
  //最大空闲连接数
  config.setMaxIdle(10)
  config.setTestOnBorrow(true)
  private val port1 = new HostAndPort("192.168.32.128",7001)
  private val port2 = new HostAndPort("192.168.32.128",7002)
  private val port3 = new HostAndPort("192.168.32.128",7003)
  private val ports = new util.HashSet[HostAndPort]()
  ports.add(port1)
  ports.add(port2)
  ports.add(port3)
  //10000代表超时时间（10秒）
//  val pool = new JedisPool(config, "192.168.32.128", 7001, 10000)
  private val cluster = new JedisCluster(ports,10000,config)
  def getConnection() = {
    cluster
  }


  def main(args: Array[String]) {


    val conn = JedisConnectionPool.getConnection()
        conn.set("income", "1000")

        val r1 = conn.get("xiaoniu")

        println(r1)

        conn.incrBy("xiaoniu", -50)

        val r2 = conn.get("xiaoniu")

        println(r2)

//        conn.close()



//    val r = conn.hkeys("*")
//    import scala.collection.JavaConversions._
//    for (p <- r) {
//      println(p + " : " + conn.get(p))
//    }
//    conn.close()
  }


}

