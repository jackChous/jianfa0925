package day02

/**
  * 让Object继承抽象类
  *1.Object可以继承抽象类，同时实现抽象方法，但是在object中不可以进行传参
  * 也就是不能实现构造器
  */
abstract class absObject(var message:String) {
  def sayHello(name:String)
}

//object中没有构造器
object abs extends absObject("hello"){
  override def sayHello(name: String): Unit = {
    println(message +" , "+name)
  }

  def main(args: Array[String]): Unit = {
    abs.sayHello("zhangsan")
  }
}

