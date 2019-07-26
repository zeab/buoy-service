//Imports
import sbt._

object Dependencies {

  //List of Versions
  val V = new {
    val akka                        = "2.5.23"
    val akkaHttpTools               = "1.0.4"
    val scalaTest                   = "3.0.5"
    val scalaExtras                 = "1.1.8"
    val logbackJson                 = "6.1"
    val logback                     = "1.2.3"
  }

  //List of Dependencies
  val D = new {
    //Akka Http
    val akkaHttpTools               = "com.github.zeab" %% "akkahttptools" % V.akkaHttpTools
    //Test
    val scalaTest                   = "org.scalatest" %% "scalatest" % V.scalaTest % "test"
    val akkaTestKit                 = "com.typesafe.akka" %% "akka-testkit" % V.akka % Test
    //ScalaExtras
    val scalaExtras                 = "com.github.zeab" %% "scalaextras" % V.scalaExtras
    //Logging
    val logback                     = "ch.qos.logback" % "logback-classic" % V.logback
    val logbackLogstash             = "net.logstash.logback" % "logstash-logback-encoder" % V.logbackJson
    val akkaSlf4j                   = "com.typesafe.akka" %% "akka-slf4j" % V.akka
  }

  val rootDependencies: Seq[ModuleID] = Seq(
    D.akkaHttpTools,
    D.scalaExtras,
    D.scalaTest,
    D.akkaTestKit,
    D.logback,
    D.logbackLogstash,
    D.akkaSlf4j
  )

}
