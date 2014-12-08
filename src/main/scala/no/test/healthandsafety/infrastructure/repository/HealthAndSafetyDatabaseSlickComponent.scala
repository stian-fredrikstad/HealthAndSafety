package no.test.healthandsafety.infrastructure.repository

import no.test.healthandsafety.infrastructure.repository.dao.PageSlickComponent
import no.test.healthandsafety.model.repository.HealthAndSafetyDatabaseComponent

trait HealthAndSafetyDatabaseSlickComponent
	extends HealthAndSafetyDatabaseComponent
	with PageSlickComponent {
	self: SlickDataSource =>
}