
//Imports
import Settings._
import Dependencies._
import Docker._
import ModuleNames._

//Sbt Log Level
logLevel := Level.Info

//Add all the command alias's
CommandAlias.allCommandAlias

lazy val buoyservice = (project in file(rootKey))
  .settings(rootSettings: _*)
  .settings(libraryDependencies ++= rootDependencies)
  .settings(rootDockerSettings)
  .enablePlugins(AshScriptPlugin)
  .enablePlugins(AssemblyPlugin)
