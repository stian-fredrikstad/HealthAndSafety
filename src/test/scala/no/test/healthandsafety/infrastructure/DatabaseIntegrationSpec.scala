package no.test.healthandsafety.infrastructure

import no.test.healthandsafety.infrastructure.repository.{HealthAndSafetyDatabaseSlickComponent, SlickDataSourceTest}
import no.test.healthandsafety.model.logical.Page
import org.scalatest.{FunSpec, Matchers}

class DatabaseIntegrationSpec extends FunSpec with Matchers {

	object TestContext extends SlickDataSourceTest with HealthAndSafetyDatabaseSlickComponent
	import TestContext.pageDAO

	describe("Persisting of pages") {
		it("inserts pages with ID") {
			val pageName = "http://www.test.no"
			pageDAO.create()
			val id = pageDAO.insert(Page(pageName, 1))
			val persistedPage = pageDAO.find(id)
			persistedPage shouldBe Some(Page(pageName, 1, Some(id)))
		}
	}
}
