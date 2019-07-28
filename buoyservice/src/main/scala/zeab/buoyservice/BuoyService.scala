package zeab.buoyservice

//Imports
import akka.actor.Props
import zeab.buoyservice.httpservice.Routes
import zeab.scalaextras.sys.EnvironmentVariables
//Akka
import akka.event.LoggingAdapter
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import com.typesafe.config.ConfigFactory
//Scala
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._

object BuoyService extends App with EnvironmentVariables {

  //Akka
  implicit val system: ActorSystem = ActorSystem("BuoyService", ConfigFactory.load())
  implicit val mat: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher
  val akkaLogger: LoggingAdapter = system.log

  //Start the route timer processor so we can actually see the time events in the logs
  system.actorOf(Props[RouteTimerEventProcessor])

  //Http Server
  val httpServiceHost: String = getEnvVar[String]("HTTP_SERVICE_HOST", "0.0.0.0")
  val httpServicePort: Int = getEnvVar[Int]("HTTP_SERVICE_PORT", 8080)
  val httpServerSource: Source[Http.IncomingConnection, Future[Http.ServerBinding]] =
    Http().bind(interface = httpServiceHost, httpServicePort)
  implicit val httpBinding: Future[Http.ServerBinding] =
    httpServerSource.to(Sink.foreach { connection =>
      akkaLogger.info("Accepted new connection from {}", connection.remoteAddress)
      connection.handleWith(Routes.all)
    }).run()
  //TODO How to make this an akka log... rather than a raw log so it gets captured with AKKA_LOG_LEVEL
  akkaLogger.info(s"Http Server is now online at http://$httpServiceHost:$httpServicePort")

  scala.sys.addShutdownHook {
    httpBinding.onComplete{ _ =>
      akkaLogger.info("Http Server is now offline")
      system.terminate()
      akkaLogger.info("Terminated... Exiting")
      Await.result(system.whenTerminated, 30.seconds)
    }
  }

}
