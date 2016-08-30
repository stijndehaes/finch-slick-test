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
  "org.scalatest" %% "scalatest" % "2.2.5" % "test"
)

resolvers ++= Seq(
	 Resolver.sonatypeRepo("snapshots")
)

testOptions in Test += Tests.Setup(() => System.setProperty("ENV", "test"))

enablePlugins(JavaAppPackaging)
