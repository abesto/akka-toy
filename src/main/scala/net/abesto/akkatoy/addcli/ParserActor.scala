package net.abesto.akkatoy.addcli

import akka.actor.Actor
import akka.event.Logging
import net.abesto.akkatoy.addcli.ImplActor.Add

class ParserActor extends Actor {
  val log = Logging(context.system, this)
  override def receive: Receive = {
    case cmd@RawCommand(id, string) =>
      log.debug(s"parsing: $string")
      val words = string.split(" +").filterNot(_.isEmpty)
      words match {
        case Array("add", x, y) => context.actorSelection("../impl") ! Command(id, Add(x.toInt, y.toInt))
        case xs => context.parent ! Result(Command(id, string), s"Unable to parse command: $string")
      }
  }
}

