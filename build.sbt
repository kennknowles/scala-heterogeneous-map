name := "scala-heterogenous-map"

version := "1.0-SNAPSHOT"

organization := "com.kennknowles"

scalaVersion := "2.9.1"

scalacOptions ++= Seq("-deprecation", "-unchecked")

testOptions in Test += Tests.Argument(TestFrameworks.ScalaCheck, "-minSuccessfulTests", "500")

libraryDependencies += "org.scala-tools.testing" %% "scalacheck" % "1.9"

