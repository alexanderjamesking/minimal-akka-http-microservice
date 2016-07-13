package support

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

import scala.concurrent.ExecutionContext

trait FunctionalSpec
  extends FlatSpec
    with Matchers
    with ScalaFutures
    with IntegrationPatience
    with BeforeAndAfterAll {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher

  val config = ConfigFactory.load()
  val serverPort = PortFinder.findFreePort()
  val testServer = new TestServer(serverPort)

  override def beforeAll() = {
    testServer.start()
  }

  override def afterAll() = {
    testServer.stop()
  }

}
