name := "finagle-server"

version := "0.1"

scalaVersion := "2.11.7"

mainClass := Some("Server")

libraryDependencies ++= Seq(
  "com.twitter" %% "finagle-httpx" % "6.29.0",
  "com.typesafe" % "config" % "1.3.0"
)
