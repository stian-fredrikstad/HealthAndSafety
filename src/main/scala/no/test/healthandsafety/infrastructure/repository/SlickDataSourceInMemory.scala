package no.test.healthandsafety.infrastructure.repository

import scala.slick.driver.H2Driver

trait SlickDataSourceInMemory extends SlickDataSource {
	val profile = H2Driver

	import profile.simple._

	val database = Database.forURL("jdbc:h2:mem:healthandsafety;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
}
