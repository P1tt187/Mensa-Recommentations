name := "Mensa-Vote"

version := "1.0"

scalaVersion := "2.9.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "JBoss Repository" at "https://repository.jboss.org/nexus/content/groups/public/"

libraryDependencies += "org.hibernate" % "hibernate-core" % "4.1.7.Final"

libraryDependencies += "hsqldb" % "hsqldb" % "1.8.0.10"

libraryDependencies += "net.sourceforge.htmlunit" % "htmlunit" % "2.11"