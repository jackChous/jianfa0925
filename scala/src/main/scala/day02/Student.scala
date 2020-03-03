package day02

/**
  * 辅助构造器
  */
class Student {
  var name = ""
  var age = 0

//  辅助构造器1
  def this(name:String){
    this()
    this.name = name
    println("123")
  }
//  辅助构造器2
  def this (name:String,age:Int){
    this(name)
    this.age=age
    println("456")
  }
}

object StudentTest{
  def main(args: Array[String]): Unit = {
    val stu = new Student("zhangsan",20)
    println(stu.name)
    println(stu.age)
  }
}