name := "HealthAndSafety"

version := "1.0"

scalaVersion := "2.10.3"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/maven-releases/"

libraryDependencies ++= {
	val akkaV = "2.3.0"
	val sprayV = "1.3.1"
	Seq(
		"io.spray" % "spray-can" % sprayV,
		"io.spray" % "spray-routing" % sprayV,
		"io.spray" % "spray-client" % sprayV,
		"io.spray" % "spray-testkit" % sprayV % "test",
		"com.typesafe.akka" %% "akka-actor" % akkaV,
		"com.typesafe.akka" %% "akka-testkit" % akkaV % "test",
		"org.json4s" %% "json4s-native" % "3.2.2",
		"org.scalatest" % "scalatest_2.10" % "2.1.3" % "test",
		"org.mockito" % "mockito-core" % "1.9.5" % "test",
		"com.typesafe.slick" %% "slick" % "2.0.2",
		"com.typesafe.slick" %% "slick-extensions" % "2.0.0",
		"com.h2database" % "h2" % "1.3.170",
		"ch.qos.logback" % "logback-classic" % "1.0.13",
		"log4j" % "log4j" % "1.2.14",
		"commons-logging" % "commons-logging" % "1.1.1",
		"org.scalaj" %% "scalaj-http" % "1.1.0"
	)
}

Revolver.settings

Revolver.enableDebugging(port = 8085, suspend = false)
