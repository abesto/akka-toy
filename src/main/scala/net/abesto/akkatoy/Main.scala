package net.abesto.akkatoy

import akka.actor.{Props, ActorSystem}

object Main {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("AkkaToy")
    val cli = system.actorOf(Props[CliActor], "cli")
    cli ! CliActor.Run
  }
}
