package net.abesto.akkatoy.addcli

import akka.actor.Actor

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
      val input = readLine().trim()
      if (input.isEmpty) {
        self ! InputActor.ReadCommand
      } else {
        context.parent ! RawCommand(id, input)
      }
  }
}

object InputActor {
  object ReadCommand  
}
