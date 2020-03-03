object RecurseAndTallRecurseDemo {
  def main(args: Array[String]): Unit = {
//

//    方式1:递归
//    val result = recurseSum(100)
//    println(result)

    //    方式1:递归
    val result2 = tailRecurseSum(100,0)
    println(result2)
  }

  /**
    * 使用递归
    */
  def recurseSum(cnt:Int):Int={
    if (cnt<=1) 1
    else
      cnt+recurseSum(cnt - 1)
  }

  /**
    * 使用尾递归
    */
  def tailRecurseSum(cnt:Int,sum:Int):Int={
    if (cnt <= 0)sum
    else
      tailRecurseSum(cnt-1,cnt+sum)
  }
}
