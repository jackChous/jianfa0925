package day02

/**
  * 初始化 Apply
  * 在我们操作时，比如用Object进行定义的方法属性都会进行默认的初始化操作
  * 也就是默认调用.apply()方法
  */
class ApplyDemo(val name:String)

object ApplyDemo{
  def apply(name: String)= new ApplyDemo(name)
}

object ApplyTest {
  def main(args: Array[String]): Unit = {
    val zhangsan = ApplyDemo("zhangsan")
    println(zhangsan.name)
  }
}