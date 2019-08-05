package zeab.buoyservice

//Imports
import akka.actor.Actor
import akka.event.{Logging, LoggingAdapter}
import zeab.akkahttptools.directives.RouteTimerEvent
import io.circe.generic.auto._
import io.circe.syntax._
import org.slf4j.{Logger, LoggerFactory}

class RouteTimerEventProcessor extends Actor {

  val log: LoggingAdapter = Logging(context.system, this)
  val udp: Logger = LoggerFactory.getLogger("UDP")

  def receive: Receive = {
    case msg: RouteTimerEvent =>
      log.info(msg.asJson.noSpaces)
      //TODO Update these to be correct...
      udp.info("buoy|c|")
      udp.info("buoy|g|")
  }

  override def preStart(): Unit = {
    context.system.eventStream.subscribe(self, classOf[RouteTimerEvent])
  }

}
