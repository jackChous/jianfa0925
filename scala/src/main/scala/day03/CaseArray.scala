package day03

/**
  * 数组、集合模式匹配
  */
object CaseArray {
  def main(args: Array[String]): Unit = {
    val arr = Array(1,2,3,4)

    arr match {
      case Array(1,x,y,z) =>println(x,y,z)
      case Array(1,2,x) =>println(s"${ x }")
      case _ =>println("")
    }

    val list =List("1","2")
    list match {
      case x :: y :: Nil =>println( x, y)
      case "1" :: Nil => println("1")
      case _ =>println("Nothing...")
    }
  }
}
