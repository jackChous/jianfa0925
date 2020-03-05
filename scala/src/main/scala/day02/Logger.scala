package day02

trait Logger {
  /**
    * Trait内部可以定义变量（抽象变量），方法（抽象方法），可以实现多重继承，使用with
    *
    */
  def logs(message:String) = message
}

class log(val name:String) extends Logger{
  def makeLog(age:Int):Unit = {
    println(logs(name)+"进行打印log..."+age)
  }
}

object testLogger extends App {
  val log =new log("xiaowang")
  log.makeLog(23)
}