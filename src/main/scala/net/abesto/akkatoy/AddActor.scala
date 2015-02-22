package net.abesto.akkatoy

import akka.actor.Actor
import akka.event.Logging

class AddActor extends Actor {
  import AddActor._
  val log = Logging(context.system, this)
  var offset = 0
  override def receive: Receive = {
    case (a: Int, b: Int) => 
      log.info(s"adding $a + $b, offset $offset")
      sender ! (a + b + offset)
    case GetOffset => sender ! Offset(offset)
    case SetOffset(newOffset) => 
      log.info(s"changing offset from $offset to $newOffset")
      offset = newOffset
  }
}

object AddActor {
  object GetOffset
  case class SetOffset(newOffset: Int) 
  case class Offset(offset: Int)
}
