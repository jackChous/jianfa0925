package day03


object ScalaWC {
  def main(args: Array[String]): Unit = {
    val strings = Array("a0000038db0c302 com.yunlian.wewe,com.octinn.birthdayplus com.elinkway.infinitemovies")
    val lists = strings.toList
    val lists2 =lists.flatMap (_.split("\\s+",2).toList)
//    println(lists2)
    val t=lists2 match {
      case List(a,b) => (a,b)
      case _ =>(0,0)
    }

    println(t)
  }
}
