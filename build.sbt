ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

val akkaVer = "2.8.3"

lazy val root = (project in file("."))
  .settings(
    name := "akka-receive-timeout-bug",
    Compile / mainClass := Some("Main"),
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % "1.4.8",
      "com.typesafe.akka" %% "akka-actor" % akkaVer,
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVer,
    )
  )
