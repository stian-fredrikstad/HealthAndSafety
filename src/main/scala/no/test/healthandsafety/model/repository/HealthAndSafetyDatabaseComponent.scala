package no.test.healthandsafety.model.repository

import no.test.healthandsafety.model.repository.dao.PageDAO

trait HealthAndSafetyDatabaseComponent {
	val pageDAO: PageDAO
}

