package net.abesto.akkatoy.addcli

import akka.actor.{Actor, Props}

class CliActor extends Actor {
  private val parser = context.actorOf(Props[ParserActor], "parser")
  private val impl = context.actorOf(Props[ImplActor], "impl")
  private val input = context.actorOf(Props[InputActor], "input")
  
  private var prompt = ""
  
  override def receive: Receive = {
    case CliActor.Run => input ! InputActor.ReadCommand
    case cmd: RawCommand =>
      parser forward cmd
      input ! InputActor.ReadCommand
    case Result(cmd, output) => 
      println()
      println(s"(${cmd.id}) $output")
      print(prompt)
    case CliActor.Prompt(text) =>
      prompt = text
      print(prompt)
  }
  
}

object CliActor {
  object Run
  case class Prompt(text: String)
}
