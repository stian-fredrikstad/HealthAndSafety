package no.test.healthandsafety

import org.apache.cxf.message.MessageContentsList
import akka.util.Timeout
import scala.concurrent.duration._
import akka.actor.{ActorRef, Props, ActorRefFactory}


object `package` {
	implicit val timeout = Timeout(15.seconds)

	object helpers {

		object MessageContentListResponse {
			def unapply(mcL: MessageContentsList): Option[List[Any]] = {
					Some(mcL.toArray.toList)
			}
		}

	}

}
