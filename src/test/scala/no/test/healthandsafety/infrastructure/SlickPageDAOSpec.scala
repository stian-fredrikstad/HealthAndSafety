package no.test.healthandsafety.infrastructure

import no.test.healthandsafety.TestDAO
import no.test.healthandsafety.domain.model.Page
import org.scalatest.{Matchers, FunSpec}

class SlickPageDAOSpec extends FunSpec with Matchers {

	object TestContext extends TestDAO with SlickPageDAO {
		def databaseName = "test"
	}

	describe("Persisting of pages") {
		it("inserts pages with ID") {
			val pageName = "http://www.test.no"
			TestContext.create()
			val id = TestContext.insert(Page(pageName))
			val persistedPage = TestContext.find(id)
			persistedPage shouldBe Some(Page(pageName, Some(id)))
		}
	}
}
