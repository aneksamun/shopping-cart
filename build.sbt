ThisBuild / organization := "co.uk.redpixel.cart"
ThisBuild / scalaVersion := "2.13.10"

val CatsEffectVersion = "3.4.8"
val BetterMonadicVersion = "0.3.1"
val SttpClientVersion = "3.8.13"
val CirceVersion = "0.14.5"
val MUnitVersion = "1.0.7"
val MUnitScalacheckVersion = "0.7.29"

lazy val root = (project in file(".")).settings(
  name := "shopping-cart",
  libraryDependencies ++= Seq(
    // "core" module - IO, IOApp, schedulers
    // This pulls in the kernel and std modules automatically.
    "org.typelevel" %% "cats-effect" % CatsEffectVersion,
    // concurrency abstractions and primitives (Concurrent, Sync, Async etc.)
    "org.typelevel" %% "cats-effect-kernel" % CatsEffectVersion,
    // standard "effect" library (Queues, Console, Random etc.)
    "org.typelevel" %% "cats-effect-std" % CatsEffectVersion,
    // client for HTTP
    "com.softwaremill.sttp.client3" %% "core" % SttpClientVersion,
    "com.softwaremill.sttp.client3" %% "circe" % SttpClientVersion,
    "com.softwaremill.sttp.client3" %% "async-http-client-backend-cats" % SttpClientVersion,
    // JSON library
    "io.circe" %% "circe-generic" % CirceVersion,
    // better monadic for compiler plugin as suggested by documentation
    compilerPlugin("com.olegpy" %% "better-monadic-for" % BetterMonadicVersion),
    "org.typelevel" %% "munit-cats-effect-3" % MUnitVersion % Test,
    "org.scalameta" %% "munit-scalacheck" % MUnitScalacheckVersion % Test
  )
)
