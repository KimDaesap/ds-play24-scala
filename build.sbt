import sbt.Project.projectToRef

lazy val scalaV = "2.11.7"
lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js
lazy val jsProjects = Seq(client)

lazy val shared = (crossProject.crossType(CrossType.Pure) in file("project-shared"))
  .jsConfigure(_ enablePlugins ScalaJSPlay)
  .settings(
    name := "ds-play24-scala",
    version := "0.1-SNAPSHOT",
    homepage := Some(url("https://github.com/KimDaesap/ds-play24-scala")),
    scalaVersion := scalaV,
    resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "scalatags" % "0.5.4",
      "be.doeraene" %% "scalajs-jquery_sjs0.6" % "0.9.0",
      "com.greencatsoft" %% "scalajs-angular_sjs0.6" % "0.6",
      "com.github.japgolly.scalacss" %%% "core" % "0.4.0"))

lazy val server = (project in file("project-server"))
  .enablePlugins(PlayScala)
  .aggregate(jsProjects.map(projectToRef): _*)
  .dependsOn(sharedJvm)
  .settings(
    scalaVersion := scalaV,
    scalaJSProjects := jsProjects,
    pipelineStages := Seq(scalaJSProd, rjs, digest, gzip),
    routesGenerator := InjectedRoutesGenerator,
    RjsKeys.mainModule := "app",
    libraryDependencies ++= Seq(
      cache,
      ws,
      "org.scalatestplus" %%% "play" % "1.4.0" % Test,
      "org.webjars" %%% "webjars-play" % "2.4.0",
      "org.reactivemongo" %%% "play2-reactivemongo" % "0.11.10",
      //--- Webjar dependencies.
      "org.webjars" % "requirejs" % "2.2.0",
      "org.webjars" % "jquery" % "2.2.1",
      "org.webjars" % "bootstrap" % "3.3.6" exclude("org.webjars", "jquery"),
      "org.webjars.bower" % "angularjs" % "1.5.1"))

lazy val client = (project in file("project-client"))
  .enablePlugins(ScalaJSPlugin, ScalaJSPlay)
  .dependsOn(sharedJs)
  .settings(
    scalaVersion := scalaV,
    testFrameworks += new TestFramework("utest.runner.Framework"),
    persistLauncher in Compile := true,
    persistLauncher in Test := false,
    scalaJSUseRhino in Global := false,
    skip in packageJSDependencies := false,
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "utest" % "0.4.3" % Test),
    jsDependencies ++= Seq(
      RuntimeDOM % Test,
      "org.webjars" % "jquery" % "2.2.1" / "jquery.js" % Test))

// loads the Play project at sbt startup
onLoad in Global := (Command.process("project server", _: State)) compose (onLoad in Global).value