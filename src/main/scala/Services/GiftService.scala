package Services

import Models.Gift
import io.finch.circe._
import io.finch.{Endpoint, _}

import scala.collection.mutable
import scala.util.Random
import io.circe.generic.auto._

/**
  * Created by stijndehaes on 26/08/16.
  */
object GiftService {

  val gifts: mutable.HashMap[Long, Gift] = new mutable.HashMap()

  def getGift: Endpoint[Gift] = get("gift" / long) {
    (id: Long) => {
      Ok(gifts(id))
    }
  }

  def putGift: Endpoint[Gift] = post("gift" ? body.as[Gift]) {
    (gift: Gift) => {
      val id: Long = gift.id.getOrElse({
        Random.nextLong()
      })
      gift.id = Option(id)
      gifts.put(id, gift)
      Ok(gift)
    }
  }

  def api = getGift :+: putGift
}
