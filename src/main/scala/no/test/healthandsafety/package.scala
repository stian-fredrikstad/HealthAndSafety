package no.test.healthandsafety

import akka.util.Timeout
import scala.concurrent.duration._


object `package` {
	implicit val timeout = Timeout(15.seconds)

}
