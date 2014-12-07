package no.test.healthandsafety.domain

import no.test.healthandsafety.domain.model.Page

trait PageDAO {
	def insert(page: Page): Int
	def find(id: Int): Option[Page]
}