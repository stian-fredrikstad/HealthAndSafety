name := "HealthAndSafety"

version := "1.0"

scalaVersion := "2.10.3"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/maven-releases/"

libraryDependencies ++= {
	val akkaV = "2.3.0"
	val sprayV = "1.3.1"
	val cxfV = "2.7.8"
	val camelV = "2.13.1"
	Seq(
		"io.spray" % "spray-can" % sprayV,
		"io.spray" % "spray-routing" % sprayV,
		"io.spray" % "spray-client" % sprayV,
		"io.spray" % "spray-testkit" % sprayV % "test",
		"com.typesafe.akka" %% "akka-actor" % akkaV,
		"com.typesafe.akka" %% "akka-camel" % akkaV,
		"com.typesafe.akka" %% "akka-testkit" % akkaV % "test",
		"org.json4s" %% "json4s-native" % "3.2.2",
		"org.apache.camel" % "camel-cxf" % camelV exclude("org.apache.geronimo.specs", "geronimo-javamail_1.4_spec"),
		"org.apache.camel" % "camel-mail" % camelV,
		"org.apache.camel" % "camel-http4" % camelV exclude("org.apache.geronimo.specs", "geronimo-servlet_3.0_spec"),
		"org.apache-extras.camel-extra" % "camel-jcifs" % camelV,
		"org.apache.cxf" % "cxf-tools-wsdlto-core" % cxfV exclude("org.apache.geronimo.specs", "geronimo-javamail_1.4_spec"),
		"org.apache.cxf" % "cxf-tools-wsdlto-databinding-jaxb" % cxfV,
		"org.apache.cxf" % "cxf-tools-wsdlto-frontend-jaxws" % cxfV,
		"org.scalatest" % "scalatest_2.10" % "2.1.3" % "test",
		"org.mockito" % "mockito-core" % "1.9.5" % "test",
		"com.typesafe.slick" %% "slick" % "2.0.2",
		"com.typesafe.slick" %% "slick-extensions" % "2.0.0",
		"com.h2database" % "h2" % "1.3.170",
		"ch.qos.logback" % "logback-classic" % "1.0.13",
		"log4j" % "log4j" % "1.2.14",
		"commons-logging" % "commons-logging" % "1.1.1",
		"joda-time" % "joda-time" % "2.3",
		"org.joda" % "joda-convert" % "1.2"
	)
}

Revolver.settings

Revolver.enableDebugging(port = 8085, suspend = false)
