package day04

import akka.actor.Actor

class LSActor extends Actor{
  override def receive: Receive = {
    case "start" =>println("李四说: hello!")
    case "李四" =>{
      println("李四说：你吃饭了没？")
      Thread.sleep(1000)
      sender() ! "张三"
    }
  }
}
