name := "fitInnsbruckApp"

version := "1.0"

lazy val `fitinnsbruckapp` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq( jdbc , anorm , cache , ws )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  javaCore
)

libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1201-jdbc4"

libraryDependencies += "net.sf.opencsv" % "opencsv" % "2.3"

