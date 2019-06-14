package zeab.buoyservice.webservice

//Imports
import zeab.buoyservice.BuoyService.getEnvVar
//Java
import java.util.UUID
//Akka
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object Routes extends DirectiveExtensions {

  val livenessPath: String = getEnvVar[String]("LIVENESS_PATH", "liveness")
  val readinessPath: String = getEnvVar[String]("READINESS_PATH", "readiness")

  def all: Route =
    logRoute { ingressRoute ~ livenessRoute ~ readinessRoute }

  //Routes dealing with basic ingress checks
  def ingressRoute: Route = {
      pathPrefix("ingress") {
        get {
          complete(OK, s"Get Ingress - ${UUID.randomUUID}")
        } ~
          post {
            complete(Created, s"Post Ingress - ${UUID.randomUUID}")
          } ~
          put {
            complete(Accepted, s"Put Ingress - ${UUID.randomUUID}")
          } ~
          delete {
            complete(OK, s"Delete Ingress - ${UUID.randomUUID}")
          }
      }
    }

  def livenessRoute: Route =
    pathPrefix(livenessPath) {
      get { complete(OK, s"Service is Live - ${UUID.randomUUID}") }
    }

  def readinessRoute: Route =
    pathPrefix(readinessPath) {
      get { complete(OK, s"Service is Ready - ${UUID.randomUUID}") }
    }

}
