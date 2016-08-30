package Services

import Models.Gift
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, RequestBuilder, Response}
import com.twitter.io.Buf
import org.scalatest.{Matchers, fixture}
import io.finch.test.ServiceSuite

/**
  * Created by stijndehaes on 29/08/16.
  */
class GiftServiceTest extends fixture.FlatSpec with ServiceSuite with Matchers {

  override def createService(): Service[Request, Response] = {
    GiftService.gifts.put(1, Gift(Option(1), "hondje", Option("Dit is een bull dog terrier")))
    endpoint.getService
  }

  "get test" should "be able to get a gift" in { f =>
    val request: Request = RequestBuilder()
      .url("http://localhost:8080/gift/1").buildGet()
    val result: Response = f(request)
    result.statusCode shouldBe 200
    result.getContentString() should include("\"id\":1")
    result.getContentString() should include("hondje")
    result.getContentString() should include("Dit is een bull dog terrier")
  }

  "post test" should "be able to get a gift" in { f =>
    val request: Request = RequestBuilder()
      .url("http://localhost:8080/gift").buildPost(
      Buf.Utf8(s"""
             |{
                |"name": "hondje",
                |"description": "Dit is een bull dog terrier"
             |}
             """.stripMargin)
    )
    val result: Response = f(request)
    result.statusCode shouldBe 200
    result.getContentString() should include("\"id\"")
    result.getContentString() should include("hondje")
    result.getContentString() should include("Dit is een bull dog terrier")
  }

}
