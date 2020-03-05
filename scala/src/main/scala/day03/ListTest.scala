package day03

/**
  * 用递归方式给List中每个元素都加上前缀，并打印加上
  * 前缀的元素
  */
class ListTest {
  def List2X(list:List[Int],x:String):Unit = {
    if(list !=Nil){
      println(x + list.head)
      List2X(list.tail,x)
    }
  }
}

object ListTest extends App {
  val list =List(1,2,3,4,5)
  val x ="=="
  val test = new ListTest
  test.List2X(list,x)
}