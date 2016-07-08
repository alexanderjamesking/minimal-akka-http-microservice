import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

object AkkaHttpMicroservice extends App {

  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val config = ConfigFactory.load()
  val logger = Logging(system, getClass)

  val routes: Route = {

    path("ping") {
      get {
        logger.debug("ping")
        complete("Ping OK!")
      }
    }

  }

  Http().bindAndHandle(routes, "0.0.0.0", 9000)
}
