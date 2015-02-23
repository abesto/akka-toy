package net.abesto.akkatoy.addcli

import akka.actor.{ActorSystem, Props}

object Main {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("AkkaToy")
    val cli = system.actorOf(Props[CliActor], "cli")
    cli ! CliActor.Run
  }
}
