name := """finch-seed"""

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

// what class starts the server
mainClass in (Compile, run) := Some("Main")

// global package settings
packageDescription := "Custom application configuration"

libraryDependencies ++= Seq(
  "com.github.finagle" %% "finch-core" % "0.10.0",
	"com.github.finagle" %% "finch-circe" % "0.10.0",
	"io.circe" %% "circe-generic" % "0.3.0",
  "com.github.finagle" %% "finch-test" % "0.10.0" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.5" % "test",
  "org.scalatest" %% "scalatest" % "2.2.5" % "test",
  "com.typesafe.slick" %% "slick" % "3.0.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.h2database" % "h2" % "1.3.166"
)

resolvers ++= Seq(
	 Resolver.sonatypeRepo("snapshots")
)

testOptions in Test += Tests.Setup(() => System.setProperty("ENV", "test"))

enablePlugins(JavaAppPackaging)
