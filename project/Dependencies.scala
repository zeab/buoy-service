//Imports
import sbt._

object Dependencies {

  //List of Versions
  val V = new {
    val akka                        = "2.5.22"
    val akkaHttpTools               = "1.0.+"
    val scalaTest                   = "3.0.5"
    val scalaExtras                 = "1.1.0"
  }

  //List of Dependencies
  val D = new {
    //Akka
    val akkaStream                  = "com.typesafe.akka" %% "akka-stream" % V.akka
    //Akka Http
    val akkaHttpTools               = "com.github.zeab" %% "akkahttptools" % V.akkaHttpTools
    //Test
    val scalaTest                   = "org.scalatest" %% "scalatest" % V.scalaTest % "test"
    val akkaTestKit                 = "com.typesafe.akka" %% "akka-testkit" % V.akka % Test
    //ScalaExtras
    val scalaExtras                 = "com.github.zeab" %% "scalaextras" % V.scalaExtras
  }

  val rootDependencies: Seq[ModuleID] = Seq(
    D.akkaStream,
    D.akkaHttpTools,
    D.scalaExtras,
    D.scalaTest,
    D.akkaTestKit
  )

}
