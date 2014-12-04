package no.test.healthandsafety

import org.json4s.{DefaultFormats, Formats}
import spray.httpx.Json4sSupport


object Json4sProtocol extends Json4sSupport {
	implicit def json4sFormats: Formats = DefaultFormats
}
