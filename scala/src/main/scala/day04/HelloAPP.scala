package day04

import akka.actor.{ActorRef, ActorSystem, Props}

object HelloAPP extends App{
//  创建ActorSystem
  val actorSystem =ActorSystem("actor")
//  通过actorSystem创建ActorRef
  private val ls: ActorRef = actorSystem.actorOf(Props[ZZActor],"ls")

//  通过actorSystem创建LSActorRef
  private val zz: ActorRef = actorSystem.actorOf(Props(new ZZActor(ls)),"zz" )
  ls !"start"
  zz !"start"

}
