
organization := "proseand.co.nz"

name := "Lift Dropbox Notepad"

version := "0.1"

scalaVersion := "2.10.3"

scalacOptions <<= scalaVersion map { v: String =>
  val opts = "-deprecation" :: "-unchecked" :: Nil
  if (v.startsWith("2.9.")) opts else opts ++ ("-feature" :: "-language:postfixOps" :: Nil)
}

seq(webSettings :_*)

port in container.Configuration := 8080

EclipseKeys.withSource := true

transitiveClassifiers := Seq("sources")

resolvers ++= Seq(
  "Sonatype snapshots"             at "http://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype releases"              at "http://oss.sonatype.org/content/repositories/releases",
  "Java.net Maven2 Repository"     at "http://download.java.net/maven/2/"
)

libraryDependencies ++= {
  val liftVersion = "2.5.1" // Put the current/latest lift version here
  Seq(
    "net.liftweb"      %% "lift-webkit"          % liftVersion          % "compile" withSources(),
    "net.liftweb"      %% "lift-mapper"          % liftVersion          % "compile",
    "net.liftweb"      %% "lift-squeryl-record"  % liftVersion          % "compile",
    "net.liftweb"      %% "lift-wizard"          % liftVersion          % "compile",
    "net.liftweb"      %% "lift-testkit"         % liftVersion          % "compile",
    "net.liftmodules"  %% "fobo_2.5"             % "1.0"                % "compile",
    "com.dropbox.core" %  "dropbox-core-sdk"     % "1.7.5"              % "compile",
    "net.liftmodules"  %% "omniauth_2.5"         % "0.7-SNAPSHOT"       % "compile"
    )
}

//"net.liftmodules"  %% "lift-jquery-module"   % (liftVersion+"-2.2")            % "compile", 
//"net.liftmodules"  %% "fobo-twitter-bootstrap_2.5" % "0.2.0-SNAPSHOT" % "compile"  
//"net.liftmodules"  %% "fobo"                 % (liftVersion+"-0.9.3-SNAPSHOT") withSources()

// Customise container dependencies
libraryDependencies ++= Seq(
  "org.eclipse.jetty"        % "jetty-webapp"   % "8.1.12.v20130726"    % "container,test",
  // @workaround https://github.com/sbt/sbt/issues/499#issuecomment-8794028
  "org.eclipse.jetty.orbit"  % "javax.servlet"  % "3.0.0.v201112011016" % "container,test" artifacts Artifact("javax.servlet", "jar", "jar")
)

// Customize any further dependencies as desired
libraryDependencies ++= Seq(
  "com.jolbox"               % "bonecp"         % "0.7.1.RELEASE"       % "compile->default",
  "javax.servlet"            % "servlet-api"    % "2.5"                 % "provided->default",
  "org.slf4j"                % "slf4j-log4j12"  % "1.7.5"               % "compile->default",
  "com.h2database"           % "h2"             % "1.3.173"
)
