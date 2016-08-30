package Services

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import io.circe.generic.auto._
import io.finch.circe._

/**
  * Created by stijndehaes on 30/08/16.
  */
object endpoint {

  def getService: Service[Request, Response] = {
    GiftService.api.toService
  }
}
