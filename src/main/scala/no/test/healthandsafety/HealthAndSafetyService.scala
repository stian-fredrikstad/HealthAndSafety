package no.test.healthandsafety

import akka.actor.{Actor, ActorLogging, ActorRefFactory}
import spray.routing.{HttpService}

class HealthAndSafetyServiceActor extends Actor with HealthAndSafetyService with ActorLogging {
	def actorRefFactory: ActorRefFactory = context

	def receive: Receive = {
		val logMeassage: PartialFunction[Any, Any] = {case msg => {
			log.debug("Message: " + msg)
			msg
		}}
		logMeassage andThen runRoute(routes)
	}
}

trait HealthAndSafetyService extends HttpService {

	implicit def executionContext = actorRefFactory.dispatcher

	val routes =
		get {
			path("hello") {
				complete("world")
			} ~
			path(Segment) {
				(path: String) => {
					complete(path)
				}
			}
		}
}
