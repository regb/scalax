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
  val commit = "580de97246a4a6e5158841f932eb263d11b49d57"
  val githubLink = s"git://github.com/regb/scala-smtlib.git#$commit"
  RootProject(uri(githubLink))
}
