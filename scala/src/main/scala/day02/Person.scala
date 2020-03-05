package day02

/**
  * 继承
  * 在Scala中继承特性和Java一样，都是子类继承父类，拥有父类的方法和属性
  * 当然私有的除外，同时也可以去覆盖父类的方法和属性
  */
class Person {
  private var name = "zhangsan"

  def getName = name
}

class Students extends Person{
   var score = "100"
  def getScore =score

  override def getName: String = score + " " +super.getName
}

object perTest{
  def main(args: Array[String]): Unit = {
    val stu = new Students
    println(stu.getName)
    println(stu.getScore)
    println(stu.score)
  }
}