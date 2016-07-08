package functional.support

import java.io.IOException
import java.net.ServerSocket

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import server.Routes

trait BaseTestTrait extends FlatSpec with Matchers with ScalaFutures with IntegrationPatience with BeforeAndAfterAll with Routes {

  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val config = ConfigFactory.load()
  override val logger: LoggingAdapter = Logging(system, getClass)

  var serverBinding: Option[ServerBinding] = None

  val serverPort = findFreePort()

  override def beforeAll() = {
    serverBinding = Some(Http().bindAndHandle(routes, "0.0.0.0", serverPort).futureValue)
  }

  override def afterAll() = {
    serverBinding.get.unbind().futureValue
  }

  def findFreePort(): Int = {
    val socket: ServerSocket = new ServerSocket(0)
    var port = -1

    try {
      socket.setReuseAddress(true)
      port = socket.getLocalPort

      try {
        socket.close()
      } catch {
        // Ignore IOException on close()
        case e: IOException =>
      }
    } catch{
      case e: IOException =>
    } finally {
      if (socket != null) {
        try {
          socket.close()
        } catch {
          case e: IOException =>
        }
      }
    }

    if(port == -1) throw new IllegalStateException("Could not find a free TCP/IP port to start embedded Jetty HTTP Server on")
    else port
  }
}
