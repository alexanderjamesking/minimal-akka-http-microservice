package functional

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import support.FunctionalSpec

class NotFoundSpec extends FunctionalSpec {

  "a url that does not match" should "return a 404" in {
    val httpResponse = Http().singleRequest(HttpRequest(uri = s"http://localhost:$serverPort/foo")).futureValue
    httpResponse.status.intValue() shouldBe 404
  }

}
