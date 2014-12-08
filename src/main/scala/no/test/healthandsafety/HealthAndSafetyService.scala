package no.test.healthandsafety

import akka.actor.{Actor, ActorLogging, ActorRefFactory}
import no.test.healthandsafety.model.logical.Page
import spray.http.StatusCodes
import spray.routing.{HttpService}

class HealthAndSafetyServiceActor extends Actor with HealthAndSafetyService with ActorLogging {
	def actorRefFactory: ActorRefFactory = context

	def receive: Receive = {
		val logMeassage: PartialFunction[Any, Any] = {
			case msg => {
				log.debug("Message: " + msg)
				msg
			}
		}
		logMeassage andThen runRoute(routes)
	}
}

trait HealthAndSafetyService extends HttpService {

	implicit def executionContext = actorRefFactory.dispatcher

	import Json4sProtocol._

	val routes =
		path("hello") {
			get {
				complete("world")
			}
		} ~
		path("page" / IntNumber) { id =>
			get { requestContext =>
				val page = DatabaseContext.pageDAO.find(id)
				if(page.isDefined) requestContext.complete(page.get)
				else requestContext.complete(StatusCodes.NotFound)
			}
		} ~
		path("page") {
			put {
				entity(as[Page]) { page => requestContext =>
					val id = DatabaseContext.pageDAO.insert(page)
					println(page)
					requestContext.complete(page.copy(id = Some(id)))
				}
			}
		}
}
