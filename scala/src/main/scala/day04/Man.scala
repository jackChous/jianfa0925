package day04

/**
  * 隐式转换  超人变身转换
  */
class Man(val name:String)

class superman(val name:String){
  def mans =println(name+" to superman~~~")
}

object manTest{
  def main(args: Array[String]): Unit = {

//    导入隐式转换
    import day04.Man2Sup.man2SuperMan
    val man = new Man("huangxiangju")
    man.mans
  }
}