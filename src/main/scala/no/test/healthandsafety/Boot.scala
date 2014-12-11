package no.test.healthandsafety

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}
import no.test.healthandsafety.infrastructure.repository.{SlickDataSourceInMemory, HealthAndSafetyDatabaseSlickComponent}
import spray.routing.SimpleRoutingApp

object Boot extends App with SimpleRoutingApp {

	implicit val system = ActorSystem("on-spray-can")
	val environment = if (args.length == 1) args(0).toString else "development"
	var settings = loadSettings(environment)
	initializeDatabase()

	val healthandsafetyService = system.actorOf(Props[HealthAndSafetyServiceActor], "healthandsafety-service")

	startServer(interface = settings.serverInterface, port = settings.serverPort) {
		pathPrefix("test") { ctx => healthandsafetyService ! ctx}
	}

	def loadSettings(environment: String): Settings = {

		/** ConfigFactory.load() defaults to the following in order:
			* system properties
			* application.conf
			* application.json
			* application.properties
			* reference.conf
			*
			* So a system property set in the application will override file properties
			* if it is set before ConfigFactory.load is called.
			* eg System.setProperty("environment", "production")
			*/
		val envConfig = ConfigFactory.load("application")

		/** ConfigFactory.load(String) can load other files.
			* File extension must be conf, json, or properties.
			* The extension can be omitted in the load argument.
			*/
		val config = ConfigFactory.load(environment) // eg "test" or "test.conf" etc

		/** Libraries and frameworks should contain a reference.conf
			* which can then be validated using:
			* config.checkValid(ConfigFactory.defaultReference(), "configurableApp")
			*/

		new Settings(config)
	}

	def initializeDatabase() {
		DatabaseContext.pageDAO.create()
	}

}

class Settings(config: Config) {
	val serverInterface = config getString "server.interface"
	val serverPort = config getInt "server.port"
	val serverHostname = config getString "server.hostname"
	val serverUseHttps = config getBoolean "server.useHttps"
	val serverUrl = if(serverUseHttps) {
		s"https://$serverHostname"
	} else {
		s"http://$serverHostname:$serverPort"
	}
}

object DatabaseContext extends SlickDataSourceInMemory with HealthAndSafetyDatabaseSlickComponent
