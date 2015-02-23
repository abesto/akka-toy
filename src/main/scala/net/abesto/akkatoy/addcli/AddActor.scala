package net.abesto.akkatoy.addcli

import akka.actor.Actor
import akka.event.Logging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class AddActor extends Actor {
  import net.abesto.akkatoy.addcli.AddActor._
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
