package Models
import slick.driver.H2Driver.api._

/**
  * Created by stijndehaes on 26/08/16.
  */
case class Gift(id: Option[Int], name: String, description: Option[String])

object Gifts {
  class Gifts(tag: Tag) extends Table[Gift] (tag, "GIFTS") {
    def id = column[Int]("GIFT_ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("GIFT_NAME")
    def description = column[Option[String]]("GIFT_DESCRIPTION")
    def * = (id.?, name, description) <> (Gift.tupled, Gift.unapply)
  }
  val table = TableQuery[Gifts]
}
