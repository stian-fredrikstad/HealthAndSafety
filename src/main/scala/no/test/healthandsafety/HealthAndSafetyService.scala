package no.test.healthandsafety

import akka.actor._
import no.test.healthandsafety.PageActorProtocol.{ScheduleStarted, ScheduleStopped, PageNotFound, FindPage}
import no.test.healthandsafety.model.logical.Page
import no.test.healthandsafety.model.repository.dao.PageId
import spray.http.StatusCodes
import spray.routing.{RequestContext, HttpService}

class HealthAndSafetyServiceActor(val healthCheckActorProps: HealthCheckActorProps) extends Actor with HealthAndSafetyService with ActorLogging {
	def actorRefFactory: ActorRefFactory = context

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

	import akka.pattern._

	def healthCheckActorProps: HealthCheckActorProps

	val pageActor = actorRefFactory.actorOf(healthCheckActorProps.pageActorProps, "page-actor")

	implicit def executionContext = actorRefFactory.dispatcher

	import Json4sProtocol._

	val routes =
		path("page" / IntNumber / "stop") { id =>
			get { requestContext =>
				pageActor ? StopSchedule(PageId(id)) pipeTo createResponder(requestContext)
			}
		} ~
		path("page" / IntNumber / "start") { id =>
			get { requestContext =>
				pageActor ? StartSchedule(PageId(id)) pipeTo createResponder(requestContext)
			}
		} ~
		path("page" / IntNumber) { id =>
			get { requestContext =>
				pageActor ? FindPage(id) pipeTo createResponder(requestContext)
			}
		} ~
		path("page") {
			put {
				entity(as[NewPageJSON]) { page => requestContext =>
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
		case ScheduleStopped => requestContext.complete(StatusCodes.OK)
		case ScheduleStarted => requestContext.complete(StatusCodes.OK)
	}
}
