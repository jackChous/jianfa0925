package day02

/**
  * 面向对象编程之对象
  */
object ObjectTest {
  private var num = 2
  println("this is Object!!")
  def getNum = num
}

object OTest{
  def main(args: Array[String]): Unit = {

    println(ObjectTest.getNum)
  }
}