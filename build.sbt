name := "exercise-jgit"

organization := "momijikawa.exercise"

version := "0.1"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.eclipse.jgit" % "org.eclipse.jgit" % "3.7.0.201502260915-r",
  "org.slf4j" % "slf4j-nop" % "1.7.10"
)

initialCommands := "import momijikawa.exercise.exercise-jgit._"
