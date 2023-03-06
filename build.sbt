ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.1"

lazy val root = (project in file("."))
  .settings(
    name := "DeJSON",
    organization := "be.adamv",
    idePackagePrefix := Some("be.adamv.dejson"),
    resolvers += "jitpack" at "https://jitpack.io",
    libraryDependencies += "com.github.saasquatch" % "json-schema-inferrer" % "0.1.5",
    libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.14.2"
  )
