package no.test.healthandsafety.infrastructure.repository

import scala.slick.backend.DatabaseComponent
import scala.slick.driver.JdbcProfile

trait SlickDataSource {
	val database: DatabaseComponent#DatabaseDef
	val profile: JdbcProfile
}
