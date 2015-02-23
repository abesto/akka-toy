package net.abesto.akkatoy.addcli

import akka.actor.{Actor, Props}

class ImplActor extends Actor {
  val add = context.actorOf(Props[AddActor], "add")
  override def receive: Receive = {
    case Command(id, ImplActor.Add(x, y)) => add ! Command(id, AddActor.Add(x, y))
    case r: Result => context.parent forward r
  }
}

object ImplActor {
  case class Add(x: Int, y: Int)
}