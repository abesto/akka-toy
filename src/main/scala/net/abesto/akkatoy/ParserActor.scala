package net.abesto.akkatoy

import akka.actor.Actor
import akka.event.Logging
import net.abesto.akkatoy.ImplActor._

class ParserActor extends Actor {
  val log = Logging(context.system, this)
  override def receive: Receive = {
    case RawCommand(id, string) =>
      log.debug(s"parsing: $string")
      val words = string.split(" +").filterNot(_.isEmpty)
      words match {
        case Array("add", x, y) => context.actorSelection("../impl") ! Command(id, Add(x.toInt, y.toInt))
      }
  }
}

