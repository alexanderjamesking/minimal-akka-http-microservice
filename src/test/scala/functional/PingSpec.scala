package functional

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.unmarshalling.Unmarshal
import support.FunctionalSpec

class PingSpec extends FunctionalSpec {

  "ping" should "return ping OK" in {
    val httpResponse = Http().singleRequest(HttpRequest(uri = s"http://localhost:$serverPort/ping")).futureValue
    httpResponse.status.intValue() shouldBe 200

    Unmarshal(httpResponse.entity).to[String].futureValue shouldBe "Ping OK!"
  }

}
