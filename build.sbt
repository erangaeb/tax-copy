name := "tax-copy"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= {

  val akkaVersion       = "2.3.9"

  Seq(
    "com.typesafe.akka"       %% "akka-actor"               % akkaVersion,
    "com.typesafe.akka"       %% "akka-slf4j"               % akkaVersion,
    "org.slf4j"               % "slf4j-api"                 % "1.7.5",
    "ch.qos.logback"          % "logback-classic"           % "1.0.9",
    "org.scalatest"           % "scalatest_2.11"            % "2.2.1"               % "test",
    "com.itextpdf"            % "itextpdf"                  % "5.5.8"
  )
}

resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
)
