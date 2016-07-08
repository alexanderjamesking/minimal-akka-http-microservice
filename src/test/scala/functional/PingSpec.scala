package functional

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.unmarshalling.Unmarshal
import functional.support.BaseTestTrait
import org.scalatest.BeforeAndAfterAll
import server.Routes

class PingSpec extends BaseTestTrait {

  "ping" should "return ping OK" in {
    val httpResponse = Http().singleRequest(HttpRequest(uri = s"http://localhost:$serverPort/ping")).futureValue
    httpResponse.status.intValue() shouldBe 200

    Unmarshal(httpResponse.entity).to[String].futureValue shouldBe "Ping OK!"
  }


}
