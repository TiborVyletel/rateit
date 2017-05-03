import sbt._
import Dependencies._

lazy val commonSettings = Seq(
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.2",
  cancelable in Global := true
)

lazy val root = Project("root", file("."))
  .settings(commonSettings: _*)
  .aggregate(backendHttp, htmlUi)


lazy val sharedApi = (crossProject.crossType(CrossType.Pure) in file("shared-api"))
  .settings(commonSettings: _*)
  //  .enablePlugins(ScalaJSPlugin, ScalaJSWeb)
  .jsConfigure(_ enablePlugins ScalaJSWeb)

lazy val sharedApiJvm = sharedApi.jvm
lazy val sharedApiJs = sharedApi.js

lazy val backendHttp = (project in file("backend-http"))
  .settings(
             commonSettings,
             scalaJSProjects := Seq(htmlUi),
             pipelineStages in Assets := Seq(scalaJSPipeline),
             // triggers scalaJSPipeline when using compile or continuous compilation
             compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
             libraryDependencies ++= backendHttpDependencies,
             WebKeys.packagePrefix in Assets := "public/",
             managedClasspath in Runtime += (packageBin in Assets).value,
//             fork in run := true,
             watchSources ++= (watchSources in htmlUi).value
           )
  .dependsOn(sharedApiJvm)
  .enablePlugins(SbtWeb, JavaAppPackaging)

lazy val htmlUi = Project("html-ui", file("html-ui"))
  .enablePlugins(ScalaJSPlugin, ScalaJSWeb)
  .settings(commonSettings: _*)
  .settings(
             testFrameworks += new TestFramework("utest.runner.Framework"),
             scalaJSUseMainModuleInitializer := true,
             libraryDependencies ++= Dependencies.htmlUiDependencies.value
           )
  .dependsOn(sharedApiJs)



