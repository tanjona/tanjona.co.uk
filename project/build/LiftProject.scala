import sbt._

class LiftProject(info: ProjectInfo) extends DefaultWebProject(info) with bees.RunCloudPlugin {
  val liftVersion = "2.4-M1"

  override def beesApplicationId = Some("tanjona/tanjonaoncb")
  override def beesUsername = Some("tanjona")

  override def libraryDependencies = Set(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-mapper" % liftVersion % "compile->default",
	"net.liftweb" %% "lift-mongodb-record" % liftVersion % "compile->default",
	"net.liftweb" %% "lift-json" % liftVersion % "compile->default",
	"com.foursquare" %% "rogue" % "1.0.13",
	"org.eclipse.jetty" % "jetty-webapp"  % "7.0.1.v20091125" % "test->default",
	"org.ccil.cowan.tagsoup" % "tagsoup" % "1.2",
    "junit" % "junit" % "4.5" % "test->default",
    "ch.qos.logback" % "logback-classic" % "0.9.26",
    "org.scala-tools.testing" %% "specs" % "1.6.6" % "test->default",
    "com.h2database" % "h2" % "1.2.138"
  ) ++ super.libraryDependencies
}
