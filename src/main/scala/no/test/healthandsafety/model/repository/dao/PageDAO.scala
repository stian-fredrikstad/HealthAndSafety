package no.test.healthandsafety.model.repository.dao

import no.test.healthandsafety.model.logical.Page

trait PageDAO {
	def insert(page: Page): Int
	def find(id: Int): Option[Page]
	def create(): Unit
}