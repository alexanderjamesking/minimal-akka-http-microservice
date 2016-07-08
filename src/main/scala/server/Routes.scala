package server

import akka.event.LoggingAdapter
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route


trait Routes {

  val logger: LoggingAdapter

  val routes: Route = {
    path("ping") {
      get {
        complete("Ping OK!")
      }
    }
  }

}
