package no.test.healthandsafety.domain

import scala.slick.driver.JdbcProfile
import scala.slick.jdbc.JdbcBackend._

trait HealthAndSafetyDAO {
	def dbConfig: DBConfig
}

trait DBConfig {
	val profile: JdbcProfile
	val db: Database
}
