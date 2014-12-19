package no.test.healthandsafety.infrastructure.repository.dao

import no.test.healthandsafety.infrastructure.repository.SlickDataSource
import no.test.healthandsafety.model.logical.Page
import no.test.healthandsafety.model.repository.dao.PageDAO

trait PageSlickComponent {
	self: SlickDataSource =>
	import profile.simple._

	lazy val pageDAO: PageDAO = PageSlickDAO

	private object PageSlickDAO extends PageDAO {
		class Pages(tag: Tag) extends Table[Page](tag, "Page") {
			def name = column[String]("name")
			def url = column[String]("url")
			def id = column[Option[Int]]("id", O.AutoInc, O.PrimaryKey)
			def interval = column[Int]("interval")
			def status = column[Option[String]]("status")

			def * = (name, url, interval, id, status) <>(Page.tupled, Page.unapply)
		}

		val pages = TableQuery[Pages]

		def insert(page: Page): Int = {
			database.withSession {
				implicit session => {
					(pages returning pages.map(_.id.get)) += page
				}
			}
		}

		def find(id: Int): Option[Page] = {
			database.withSession {
				implicit session =>
					pages.filter(_.id === id).firstOption
			}
		}

		def create(): Unit = {
			database.withSession {
				implicit session =>
					pages.ddl.create
			}
		}

		def updateStatus(id: Int, status: String): Unit = {
			database.withSession {
				implicit session =>
					pages.filter(_.id === id).map(x => x.status).update(Option(status))
			}
		}
	}
}