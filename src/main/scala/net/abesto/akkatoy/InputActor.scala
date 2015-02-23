package net.abesto.akkatoy

import akka.actor.Actor
import akka.actor.Actor.Receive

class InputActor extends Actor {
  private var currentId = 0
  private def nextId = {
    currentId += 1
    currentId
  }
  
  override def receive: Receive = {
    case InputActor.ReadCommand =>
      val id = nextId
      context.parent ! CliActor.Prompt(s"$id> ")
      context.parent ! RawCommand(id, readLine())
  }
}

object InputActor {
  object ReadCommand  
}
