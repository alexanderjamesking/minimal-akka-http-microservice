package support

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.stream.ActorMaterializer
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import server.Routes

class TestServer(serverPort: Int) extends Routes with ScalaFutures with IntegrationPatience {

  private var serverBinding: Option[ServerBinding] = None

  def start()(implicit system: ActorSystem, materializer: ActorMaterializer) = {
    serverBinding = Some(Http().bindAndHandle(routes, "0.0.0.0", serverPort).futureValue)
  }

  def stop() = {
    serverBinding.get.unbind().futureValue
  }

}
