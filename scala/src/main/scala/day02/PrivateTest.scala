package day02

/**
  * private使用
  */
class PrivateTest {

  val name = "张三"

  //表示私有的变量，并且是只能调用不能赋值
  private val name2 = "lisi"
  //  表示私有变量 并且能调用也能赋值
  private var name3 = "wangwu"

  //这种声明方式比较严格，此变量只能在本类使用（伴生对象都使用不了）
  private[this] val name4 = "zhaoliu"

}

/**
  * 伴生对象
  */
object PrivateTest{
  def main(args: Array[String]): Unit = {
    val test = new PrivateTest
    println(test.name)
       println(test.name2)
        println(test.name3)

  }
}
