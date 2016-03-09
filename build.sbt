lazy val root = (project in file(".")).
  settings(
    organization := "com.regblanc",
    name := "scalax",
    version := "0.01",
    scalaVersion := "2.11.7",
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),

    libraryDependencies += "com.regblanc" %% "scala-smtlib" % "0.2",
    libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % Test
  )
