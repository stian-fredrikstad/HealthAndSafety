package no.test.healthandsafety.infrastructure

import no.test.healthandsafety.domain.{PageDAO, DBConfig}
import no.test.healthandsafety.domain.model.Page
import scala.slick.jdbc.JdbcBackend.Database.dynamicSession

trait SlickPageDAO extends PageDAO {
	self: DBConfig =>

	import profile.simple._

	class Pages(tag: Tag) extends Table[Page](tag, "Page") {
		def url = column[String]("url")
		def id = column[Option[Int]]("id", O.AutoInc, O.PrimaryKey)

		def * = (url, id) <>(Page.tupled, Page.unapply)
	}

	val pages = TableQuery[Pages]

	def insert(page: Page): Int = db withDynSession {
		(pages returning pages.map(_.id.get)) += page
	}

	def find(id: Int): Option[Page] = db withDynSession {
		pages.filter(_.id === id).firstOption
	}

	def create() = db withDynSession {
		pages.ddl.create
	}
}