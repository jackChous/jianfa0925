package day02

/**
  * 类型转换，可以进行子类与父类之间的转换，同时也可以进行基本数据类型
  * 之间的转换操作，而且可以先进行判断，再进行转换操作
  */
class las

class los extends las

object Lttest extends App{
//  子类返回父类的类型
//  val lost:las = new los

//  创建一个los的空类
//  var loss:los =null
//  进行类型转换
//  if(lost.isInstanceOf[los]){
//    loss = lost.asInstanceOf[los]
//  }

  val lost:las = new los
  println(lost.isInstanceOf[las])
  println(lost.getClass == classOf[las])
  println(lost.getClass == classOf[los])
//  getClass和classOf更精准的判断
}
