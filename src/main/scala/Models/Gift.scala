package Models
import slick.driver.H2Driver.api._
import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits.global
import io.finch._

/**
  * Created by stijndehaes on 26/08/16.
  */
case class Gift(id: Option[Int], name: String, description: Option[String])

object Gifts {
  class Gifts(tag: Tag) extends Table[Gift] (tag, "GIFTS") {
    def id = column[Option[Int]]("GIFT_ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("GIFT_NAME")
    def description = column[Option[String]]("GIFT_DESCRIPTION")
    def * = (id, name, description) <> (Gift.tupled, Gift.unapply)
  }
  val table = TableQuery[Gifts]

  def get(id: Int): Future[Gift] = {
    val db = Database.forConfig("h2mem1")
    try {
      db.run(Gifts.table.filter(_.id === id).result.head)
    } finally db.close()
  }

  def insertOrUpdate(gift: Gift): Future[Gift] = {
    val db = Database.forConfig("h2mem1")
    try {
      val upsert: DBIO[Option[Option[Int]]] = (Gifts.table returning Gifts.table.map(_.id)).insertOrUpdate(gift)
      db.run(upsert) flatMap {
        case Some(Some(id)) => Gifts.get(id)
        case Some(None) => Future.failed(new Exception)
        case None => Gifts.get(gift.id.get)
      }
    } finally db.close()
  }

  def delete(id: Int): Future[Int] = {
    val db = Database.forConfig("h2mem1")
    try {
      db.run(Gifts.table.filter(_.id === id).delete)
    } finally db.close()
  }
}
