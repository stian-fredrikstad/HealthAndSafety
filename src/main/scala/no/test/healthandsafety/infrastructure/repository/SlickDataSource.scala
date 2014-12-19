package no.test.healthandsafety.infrastructure.repository

import scala.slick.jdbc.JdbcBackend.Database
import scala.slick.driver.JdbcProfile

trait SlickDataSource {
	val database: Database
	val profile: JdbcProfile
}
