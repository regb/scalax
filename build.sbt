lazy val root = (project in file(".")).
  settings(
    name := "Scalax",
    version := "0.01",
    scalaVersion := "2.11.5",
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),

    libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % Test
  ).
  dependsOn(scalaSmtLib)

lazy val scalaSmtLib = {
  val commit = "b902375db875771edc28f5a456bddc841317bf7b"
  val githubLink = s"git://github.com/regb/scala-smtlib.git#$commit"
  RootProject(uri(githubLink))
}
