package no.test.healthandsafety.util

import org.slf4j.{Logger, LoggerFactory}

trait Logging {
	def logger: Logger = LoggerFactory.getLogger(getClass)

	def trace(msg: => Any) {
		if (logger.isTraceEnabled) logger.trace(msg.toString)
	}

	def debug(msg: => Any) {
		if (logger.isDebugEnabled) logger.debug(msg.toString)
	}
}
