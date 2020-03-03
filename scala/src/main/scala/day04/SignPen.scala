package day04

/**
  * 签到系统
  */
class SignPen {
  def write(content:String) =println(content)
}

class SignPens{
  def getSignPen(name: String)(implicit signPen: SignPen):Unit ={
    signPen.write(name+"...........")
  }
}

object SPTest{
  def main(args: Array[String]): Unit = {
    import day04.implicited.signPen
    val pens = new SignPens
    pens.getSignPen("zhangsan")

  }
}





