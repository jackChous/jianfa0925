package day02

abstract class absTest {
  def sayHello
//  抽象方法：没有被实现的方法
//  抽象变量：定义变量，但是没有给出具体的初始值
//  val names:String
}

class abs2(name:String) extends absTest{
  override def sayHello: Unit = println("Hello" + name)
}

object test extends App {
//  抽象类不能被实例化，但是能被继承
  val a=new abs2("zhangsan")
  a.sayHello
}