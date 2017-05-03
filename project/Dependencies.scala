import sbt.{Def, _}
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object Versions {
  val slf4j = "1.7.25"
  val akkaHttp = "10.0.5"

  val akka = "2.5.0"

  val scalatags = "0.6.3"
  val scalajsDom = "0.9.1"
}

object Libraries {
  // Libraries
  val slf4j =  "org.slf4j" % "slf4j-simple" % Versions.slf4j

  val akkaActor = "com.typesafe.akka" %% "akka-actor" % Versions.akka
  val akkaCluster = "com.typesafe.akka" %% "akka-cluster" % Versions.akka
  val akkaTyped = "com.typesafe.akka" %% "akka-typed" % Versions.akka
  val akkaStream = "com.typesafe.akka" %% "akka-stream" % Versions.akka
  val akkaHttp = "com.typesafe.akka" %% "akka-http" % Versions.akkaHttp

  lazy val scalaTags = "com.lihaoyi" %% "scalatags" % Versions.scalatags

//  lazy val scalajsDom = "org.scala-js" %%% "scalajs-dom" % Versions.scalajsDom
}

object Dependencies {
  import Libraries._

  lazy val backendHttpDependencies = Seq(akkaActor, akkaTyped, akkaStream, akkaHttp, scalaTags)

  lazy val htmlUiDependencies = Def.setting(Seq(
    "org.scala-js" %%% "scalajs-dom" % Versions.scalajsDom,
    "com.lihaoyi" %%% "scalatags" % Versions.scalatags
                                                                              ))

}