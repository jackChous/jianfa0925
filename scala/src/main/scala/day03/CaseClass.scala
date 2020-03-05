package day03

import scala.util.Random

/**
  * 对类型进行模式匹配
  */
class CaseClass {

}

object ClassTest{
  def main(args: Array[String]): Unit = {
    val clazz = new CaseClass
//    匹配数据
    val arr = Array("zhangsan",12,true,clazz,12.0)
    val arr2 =arr(Random.nextInt(arr.length))
    arr2 match {
      case str:String => println("match String~")
      case int:Int => println("match Int~")
      case bool:Boolean => println("match Boolean~")
      case caseClass:CaseClass =>println("match caseClass~")
      case dou:Double =>println("match Double~")
      case _=> println("无~")
    }
  }
}
