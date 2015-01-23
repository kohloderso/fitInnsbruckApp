name := "fitInnsbruckApp"

version := "1.0"

lazy val `fitinnsbruckapp` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq( jdbc , anorm , cache , ws )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean
)

libraryDependencies += "net.sf.opencsv" % "opencsv" % "2.3"

libraryDependencies += "com.feth" %% "play-authenticate" % "0.6.8"

resolvers += Resolver.url("play-authenticate (release)", url("http://joscha.github.io/play-authenticate/repo/releases/"))(Resolver.ivyStylePatterns)