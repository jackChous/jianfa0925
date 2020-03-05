package day02

trait TraitTest {
  /**
    * Scala中的Trait是一个特殊的概念，Trait与java中的接口类似，但是进行继承的时候
    * 不使用implement,而是使用extends进行实现。
    * 一般在spark代码中用于打印日志，如果继承多个接口(Trait)，用with。
    */
  def traitsTest(name:String)
}

trait MakeTrait{
  def maketrait(age:Int)
}

class Tra(val name:String) extends TraitTest with MakeTrait{

  override def traitsTest(name: String): Unit ={
    println("Hello "+name)
  }

  override def maketrait(age: Int): Unit = {
    println("hello "+name+" "+age)
  }
}

object TestTrait extends App{
  val tra = new Tra("lisi")
  tra.traitsTest("xiaoming")
  tra.maketrait(23)
}