package zeab.buoyservice

//Imports
import zeab.buoyservice.webservice.Routes
import zeab.scalaextras.logging.Logging
//Akka
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import com.typesafe.config.ConfigFactory
//Scala
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._
import scala.concurrent.Await

object BuoyService extends App with Logging {

  //Akka
  implicit val system: ActorSystem = ActorSystem("BuoyService", ConfigFactory.load())
  implicit val mat: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher

  //Http Server
  val httpServiceHost: String = getEnvVar[String]("WEB_SERVICE_HOST", "0.0.0.0")
  val httpServicePort: Int = getEnvVar[Int]("WEB_SERVICE_PORT", 8080)
  val httpServerSource: Source[Http.IncomingConnection, Future[Http.ServerBinding]] =
    Http().bind(interface = httpServiceHost, httpServicePort)
  implicit val httpBinding: Future[Http.ServerBinding] =
    httpServerSource.to(Sink.foreach { connection =>
      log.info("Accepted new connection from {}", connection.remoteAddress)
      connection.handleWith(Routes.all)
    }).run()
  log.info(s"Http Server is now online at http://$httpServiceHost:$httpServicePort")

  scala.sys.addShutdownHook {
    httpBinding.onComplete{ _ =>
      log.info("Http Server is now offline")
      system.terminate()
      log.info("Terminated... Exiting")
      Await.result(system.whenTerminated, 30.seconds)
    }
  }

}
