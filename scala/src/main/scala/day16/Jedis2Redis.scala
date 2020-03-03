package day16

import java.util

import redis.clients.jedis.{Jedis, JedisPool, JedisPoolConfig, ScanParams}

/**
  *  Jedis连接
  */
object Jedis2Redis {
  def main(args: Array[String]): Unit = {

    // 设置连接池的配置
    val config = new JedisPoolConfig
    // 设置最大连接数
    config.setMaxTotal(20)
    // 设置最大空闲连接数
    config.setMaxIdle(10)
    // 设置连接超时时间
    config.setMaxWaitMillis(10)

    // 创建Jedis
    // 如果有密码需要验证
    // 单机操作
//    val jedis = new Jedis("192.168.28.131",6379)
    // 连接池配置
    val pool = new JedisPool(config,"192.168.28.128",7001)
    // 获取连接
    val jedis = pool.getResource
    // 密码验证
    jedis.auth("123")

    /**
      * 字符串操作
      */
    // 添加一个字符串
    jedis.set("xiaohuang","25")
    println(jedis.get("xiaohuang"))
    // 追加字符串
    jedis.append("xiaohuang","5")
    println(jedis.get("xiaohuang"))
    // 自增  如果自增的key不存在 会创建，从0开始自增 相当于count++
    jedis.incr("count")
    jedis.incrBy("count",10)
    println(jedis.get("count"))
    // 自增浮点类型
    jedis.incrByFloat("double",10.5)
    // 自减
    jedis.decr("count")
    jedis.decrBy("count",10)
    println(jedis.get("count"))
    // 根据Key更换Value值
    jedis.getSet("xiaohuang","dahuang")
    println(jedis.get("xiaohuang"))
    // 替换，按照下标替换Value值
    jedis.setrange("xiaohuang",0,"lao")
    println(jedis.get("xiaohuang"))
    // 获取字符串长度
    println(jedis.strlen("xiaohuang"))
    // 同时设置多个KeyValue值
    jedis.mset("xiaoliu","php","count","200")
    // 获取多个key值
    val list: util.List[String] = jedis.mget("xiaoliu","count")
    println(list)

    /**
      * 集合操作 Set
      * 元素无序不可重复
      */
    // 添加元素，返回添加元素个数
    jedis.sadd("hobbys","吃","喝","玩","乐","旅游","看书")
    jedis.sadd("hobbys2","玩","吃","睡","打游戏")
    // 查询集合元素个数
    jedis.scard("hobbys")
    // 并集
    println(jedis.sunion("hobbys", "hobbys2"))
    // 交集
    println(jedis.sinter("hobbys", "hobbys2"))
    // 差集
    println(jedis.sdiff("hobbys", "hobbys2"))
    //  获取集合中的元素
    println(jedis.smembers("hobbys"))
    // 移除集合内的指定元素
    jedis.srem("hobbys","吃")

    /**
      * Zset 有序集合
      * 元素不可重复
      * 元素有序（根据Score排序）
      * score 值越大，排名越靠后
      *
      */
    // 添加元素
    jedis.zadd("price",1,"1")
    jedis.zadd("price",5,"10")
    jedis.zadd("price",2,"5.2")
    jedis.zadd("price",6,"6")
    jedis.zadd("price",7,"3")
    jedis.zadd("price",10,"五百")
    // 获取元素
    println(jedis.zrange("price", 0, -1))
    // 获取指定值(Score)的范围元素
    println(jedis.zcount("price", 1, 8))
    // 指定键在集合分值排名 从0开始
    println(jedis.zrank("price", "3"))
    // 给指定元素增加分值，返回新的分值
    jedis.zincrby("price",5.5,"3")
    // 获取元素
    println(jedis.zrange("price", 0, -1))
    // 删除指定元素
    jedis.zrem("price","五百","3")

    /**
      * List 队列
      */
    // 添加元素
    jedis.lpush("user","zhangsan","lisi","wangwu")
    // 往头部添加，列表必须存在
    jedis.lpushx("user","xiaoming")
    jedis.rpush("user","zhaoliu","tianqi","xiaohuang")
    // 查看集合长度
    jedis.llen("user")
    // 获取元素
    println(jedis.lrange("user", 0, -1))
    // 删除指定元素
    jedis.lrem("user",1,"zhangsan")
    println(jedis.lrange("user", 0, -1))
    // 修改
    jedis.lset("user",0,"xiaohong")
    // 按照索引获取元素
    jedis.lindex("user",5)

    /**
      * hash 散列
      */
    // 添加元素
    jedis.hset("users","name","xiaohuang")
    jedis.hset("users","age","18")
    jedis.hset("users","height","140")
    // 获取key
    println(jedis.hget("users", "name"))
    println(jedis.hgetAll("users"))
    // 累加
    jedis.hincrBy("users","age",10)
    println(jedis.hgetAll("users"))
    // 浮点累加
    jedis.hincrByFloat("users","height",6.5)
    println(jedis.hgetAll("users"))
    // 获取长度
    jedis.hlen("users")
    // 获取所有字段的值
    jedis.hvals("users")
    // 删除字段
    jedis.hdel("users","name")

    // 模糊查找
    jedis.hset("users","js1","v2")
    jedis.hset("users","xxxxjs","v3")
    jedis.hset("users","abcjs","v1")
    val scan = new ScanParams
    val params = scan.`match`("*js*")

//    val param: util.Collection[Array[Byte]] = params.getParams
//    val value = param.iterator()
//    while (value.hasNext){
//      val bytes = value.next()
//      bytes.foreach(t=> println(t.toString))
//    }



    // 关闭
    jedis.close()

  }
}
