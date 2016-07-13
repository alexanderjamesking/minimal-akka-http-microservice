package server

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

trait Routes {

  val routes: Route = {
    path("ping") {
      get {
        complete("Ping OK!")
      }
    }
  }

}
