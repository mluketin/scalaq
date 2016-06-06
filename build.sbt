name := "scalaq"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "NGS Releases" at "http://ngs.hr/nexus/content/repositories/releases/"

libraryDependencies ++= List(
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.revenj" % "revenj-core" %"0.9.9",
  "hr.ngs.templater" %% "templater" % "2.3.2"
)