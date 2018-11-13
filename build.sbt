import Dependencies._

lazy val root = (project in file(".")).
  dependsOn(crawler).
  settings(
    inThisBuild(List(
      organization := "services.xis.search",
      scalaVersion := "2.12.7",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "xis-search",
    javacOptions ++= Seq("-encoding", "UTF-8"),
    libraryDependencies += scalaTest % Test,
    libraryDependencies += elasticSearch
  )
