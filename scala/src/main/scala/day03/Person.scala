package day03

/**
  * 例子
  * 样例类
  */
class Person

case class Teacher(name:String) extends Person

case class Students(name:String) extends Person

object castTest{
  def main(args: Array[String]): Unit = {
    val teacher :Person =Teacher("黄香桔")
    val stu :Person = Students("zhangsan")

    teacher match {
      case Teacher(name) =>println("Teacher,name is"+name)
      case Students(name) =>println("Student,name is"+ name)
      case _ =>println("Teacher or Student,Nothing...")
    }
  }
}
