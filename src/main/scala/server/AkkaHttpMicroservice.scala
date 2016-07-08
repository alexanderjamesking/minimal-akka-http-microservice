package server

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

object AkkaHttpMicroservice extends App with Routes {

  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val config = ConfigFactory.load()
  val logger: LoggingAdapter = Logging(system, getClass)

  Http().bindAndHandle(routes, "0.0.0.0", 9000)
}
