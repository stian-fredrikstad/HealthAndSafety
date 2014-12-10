package no.test.healthandsafety

import akka.actor._
import no.test.healthandsafety.PageActorProtocol.{PageNotFound, FindPage}
import no.test.healthandsafety.model.logical.Page
import spray.http.StatusCodes
import spray.routing.{RequestContext, HttpService}

class HealthAndSafetyServiceActor extends Actor with HealthAndSafetyService with ActorLogging {

	val logMeassage: PartialFunction[Any, Any] = {
		case msg =>
			log.debug("Message: " + msg)
			msg
	}

	def receive: Receive = {
		logMeassage andThen runRoute(routes)
	}
}

trait HealthAndSafetyService extends HttpService {
	self: Actor =>

	def actorRefFactory: ActorRefFactory = context

	import akka.pattern._

	val pageActor = context.actorOf(Props[PageActor])

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
					pageActor ? FindPage(id) pipeTo createResponder(requestContext)
				}
			} ~
			path("page") {
				put {
					entity(as[Page]) { page => requestContext =>
						pageActor ? page pipeTo createResponder(requestContext)
					}
				}
			} ~
			pathPrefix("slides") {
				getFromResourceDirectory("slides")
			}

	def createResponder(requestContext: RequestContext) = {
		val props = Props(new HealthAndSafetyResponder(requestContext))
		context.actorOf(props)
	}
}

class HealthAndSafetyResponder(requestContext: RequestContext) extends Actor with ActorLogging {

	import Json4sProtocol._

	def receive = completeRequest andThen { _ =>
		self ! PoisonPill
	}

	def completeRequest: PartialFunction[Any, Unit] = {
		case p: Page => requestContext.complete(p)
		case PageNotFound => requestContext.complete(StatusCodes.NotFound)
	}
}
