package Services

import Models.{Gift, Gifts}
import io.finch.circe._
import io.finch.{Endpoint, _}

import scala.collection.mutable
import scala.util.Random
import io.circe.generic.auto._
import slick.driver.H2Driver.api._

import scala.concurrent.Future

import com.twitter.util.{Future => TFuture, Promise => TPromise, Return, Throw}
import scala.concurrent.{Future => SFuture, Promise => SPromise, ExecutionContext}
import scala.util.{Success, Failure}
import ExecutionContext.Implicits.global

/**
  * Created by stijndehaes on 26/08/16.
  */
object GiftService {

  implicit class RichTFuture[A](val f: TFuture[A]) extends AnyVal {
    def asScala(implicit e: ExecutionContext): SFuture[A] = {
      val p: SPromise[A] = SPromise()
      f.respond {
        case Return(value) => p.success(value)
        case Throw(exception) => p.failure(exception)
      }

      p.future
    }
  }

  implicit class RichSFuture[A](val f: SFuture[A]) extends AnyVal {
    def asTwitter(implicit e: ExecutionContext): TFuture[A] = {
      val p: TPromise[A] = new TPromise[A]
      f.onComplete {
        case Success(value) => p.setValue(value)
        case Failure(exception) => p.setException(exception)
      }

      p
    }
  }

  val gifts: mutable.HashMap[Int, Gift] = new mutable.HashMap()

  def getGift: Endpoint[Gift] = get("gift" / int) {
    (id: Int) => {
      Ok(Gifts.get(id).asTwitter)
    }
  }

  def putGift: Endpoint[Gift] = post("gift" ? body.as[Gift]) {
    (gift: Gift) => {
      Ok(Gifts.insertOrUpdate(gift).asTwitter)
    }
  }

  def deleteGift(): Endpoint[Int] = delete("gift" / int) {
    (id: Int) => {
      Ok(Gifts.delete(id).asTwitter)
    }
  }

  def api = getGift :+: deleteGift :+: putGift
}
