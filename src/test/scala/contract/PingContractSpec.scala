package contract

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import com.itv.scalapact.ScalaPactForger._
import com.itv.scalapact.ScalaPactMockConfig
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.implicitConversions

class PingContractSpec extends FlatSpec with Matchers with ScalaFutures with IntegrationPatience {

  implicit val system = ActorSystem()
  implicit val materialiser = ActorMaterializer()

  "Example Pact Test" should "Should respond with a ping ok message" in {
    val endPoint = "/ping"

    forgePact
      .between("Dev Ops")
      .and("AkkaHttpMicroservice")
      .addInteraction(interaction.description("Ping").uponReceiving(endPoint).willRespondWith(200, "Ping OK!"))
      .runConsumerTest { (mockConfig: ScalaPactMockConfig) =>

        val httpResponse = Http().singleRequest(HttpRequest(uri = s"${mockConfig.baseUrl}$endPoint")).futureValue
        httpResponse.status.intValue() shouldBe 200

        Unmarshal(httpResponse.entity).to[String].futureValue shouldBe "Ping OK!"
      }
  }

}
