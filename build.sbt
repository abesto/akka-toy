name := "akka-toy"

version := "1.0"

scalaVersion := "2.10.4"

val akkaVersion = "2.3.9"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test"
)