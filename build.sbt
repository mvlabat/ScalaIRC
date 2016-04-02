// Name of the project
name := "ScalaIRC"

// Project version
version := "8.0.60-R9"

// Version of Scala used by the project
scalaVersion := "2.11.7"

val qtDepSnapshots = "Test" at "file:///home/mvlabat/.ivy2/local/"
val qtDep = "net.sf.qtjambi" % "qtjambi" % "4.8.7"
val qtjambiBase = "net.sf.qtjambi" % "qtjambi-native-linux64-gcc" % "4.8.7"

resolvers += qtDepSnapshots

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true
