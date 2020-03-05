package day02

class HelloWorldTest{
  val name = "World"

  def sayHello(): Unit ={
    println("Hello"+name)
  }
}

object HelloWorld {

  def main(args: Array[String]): Unit = {
    val wordTest = new HelloWorldTest
    println(wordTest.name)
    wordTest.sayHello()
  }
}
