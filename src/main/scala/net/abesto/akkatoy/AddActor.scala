package net.abesto.akkatoy

import akka.actor.{ActorSystem, Actor}
import akka.event.Logging
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class AddActor extends Actor {
  import AddActor._
  val log = Logging(context.system, this)
  var offset = 0
  override def receive: Receive = {
    case cmd@Command(id, Add(a, b)) => context.system.scheduler.scheduleOnce(5 seconds, self, Command(id, ActuallyAdd(a, b)))
    case cmd@Command(_, ActuallyAdd(a, b)) => context.parent ! Result(cmd, s"${a + b + offset}")
    case GetOffset => sender ! Offset(offset)
    case SetOffset(newOffset) => offset = newOffset
  }
}

object AddActor {
  case class Add(x: Int, y: Int)
  case class ActuallyAdd(x: Int, y: Int)
  object GetOffset
  case class SetOffset(newOffset: Int) 
  case class Offset(offset: Int)
}
