package day03

/**
  * option类型
  * Some继承自Option，同时获取数据返回值的时候只有两个结果，一个就是Some,
  * 一个就是None，不会有Null
  * 通过map里面的get方式获取值的时候如果key不存在会返回none,
  * 不报错,但是如果再.get就会报错（源码里面会抛个异常）
  */

object CaseOption {
  def main(args: Array[String]): Unit = {
    val map =Map("zhangsan"->30,"lisi"->40,"wangwu"->50 )
    val option: Option[Int] = map.get("zhangsan")
//    map.getOrElse("zhaoliu","tianqi")

    option match {
      case Some(age) =>println(age)
      case None =>println("Sorry,nothing")
    }
  }
}
