package day04

import akka.actor.{Actor, ActorRef}

class ZZActor(val ls:ActorRef) extends Actor{
//  接收数据
  override def receive: Receive = {
    case "start" =>{
      println("张三说：hello !")
      ls !"李四"
    }
    case "张三" =>{
      println("刚吃完~~~")
      Thread.sleep(1000)
      ls ! "李四"
    }
  }
}
