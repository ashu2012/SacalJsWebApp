enablePlugins(ScalaJSPlugin)
enablePlugins(ScalaJSBundlerPlugin)


name := "scalajs-react-template"
version := "1.0"
scalaVersion := "2.12.1"

// create launcher file ( its search for object extends JSApp , make sure there is only one file)
//persistLauncher := true
//persistLauncher in Test := false

val scalaJSReactVersion = "1.0.1"
val scalaCssVersion = "0.5.3-RC1"
val reactJSVersion = "15.5.4"
version in webpack := "4.20.2"
version in startWebpackDevServer := "3.1.4"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies ++= Seq(
	 "org.scala-js" %%% "scalajs-dom" % "0.9.2",
	"com.olvind" %%% "scalajs-react-components" % "1.0.+",
  "com.lihaoyi" %%% "scalatags" % "0.6.7",
  "io.surfkit" %%% "scalajs-google-maps" % "0.0.3-SNAPSHOT",
  "com.github.japgolly.scalajs-react" %%% "core" % scalaJSReactVersion,
  "com.github.japgolly.scalajs-react" %%% "extra" % scalaJSReactVersion,
  "com.github.japgolly.scalacss" %%% "core" % scalaCssVersion,
  "com.github.japgolly.scalacss" %%% "ext-react" % scalaCssVersion,
  "org.webjars.npm" % "loose-envify" % "1.1.0",
  "org.webjars.npm" % "js-tokens" % "4.0.0"

)

// React itself
//   (react-with-addons.js can be react.js, react.min.js, react-with-addons.min.js)
//DOM, which doesn't exist by default in the Rhino runner. To make the DOM available in Rhino
jsDependencies ++= Seq(
  "org.webjars.npm" % "react" % reactJSVersion / "react-with-addons.js" commonJSName "React" minified "react-with-addons.min.js",
  "org.webjars.npm" % "react-dom" % reactJSVersion / "react-dom.js" commonJSName "ReactDOM" minified "react-dom.min.js" dependsOn "react-with-addons.js"
)


// *****************************************************************************
// Settings
// *****************************************************************************




lazy val SuiVersion   = "0.68.5"
lazy val EuiVersion   = "0.6.1"
lazy val MuiVersion   = "0.18.1"
lazy val reactVersion = "15.5.4"

lazy val bundlerSettings =
  Seq(
    version in webpack := "4.20.2",
    scalaJSUseMainModuleInitializer := true,
    scalaJSUseMainModuleInitializer.in(Test) := false,
    artifactPath.in(Compile, fastOptJS) := ((crossTarget in (Compile, fastOptJS)).value /
      ((moduleName in fastOptJS).value + "-opt.js")),
    webpackResources :=
      webpackResources.value +++
        PathFinder(
          Seq(
            baseDirectory.value / "index.html"
          )) ** "*.*",
    webpackEmitSourceMaps := false,
    webpackConfigFile in (Compile, fastOptJS) := Some(
        baseDirectory.value / "scalajs.webpack.config.js"),
   // useYarn := true,
    npmDependencies.in(Compile) := Seq(
     // "elemental"                         -> EuiVersion,
    //  "highlight.js"                      -> "9.9.0",
    //  "material-ui"                       -> MuiVersion,
    //  "react"                             -> reactVersion,
    //  "react-dom"                         -> reactVersion,
    //  "react-addons-create-fragment"      -> reactVersion,
    //  "react-addons-css-transition-group" -> "15.0.2",
    //  "react-addons-pure-render-mixin"    -> "15.5.2",
    //  "react-addons-transition-group"     -> "15.0.0",
    //  "react-addons-update"               -> "15.5.2",
    //  "react-geomicons"                   -> "2.1.0",
    //  "react-infinite"                    -> "0.11.0",
    //  "react-select"                      -> "1.0.0-rc.5",
    //  "react-slick"                       -> "0.14.11",
    //  "react-spinner"                     -> "0.2.7",
    //  "react-tagsinput"                   -> "3.16.1",
   //   "react-tap-event-plugin"            -> "2.0.1",
    //  "semantic-ui-react"                 -> SuiVersion,
    //  "svg-loader"                        -> "0.0.2",
   //   "snabbdom"                          -> "0.5.3",
   //   "font-awesome"                      -> "4.7.0",
    //  "url-loader"                        -> "0.5.9",
      "firebase"                          ->  "5.4.2",
      "firebase-admin"                    -> "6.0.0",
      "webpack"                            -> "4.18.1",
      "webpack-merge"                      -> "4.1.0",
      "file-loader"                       -> "2.0.0",
      "jquery"                             ->"3.3.1"

    )
  )
scalaJSUseMainModuleInitializer := true
lazy val settings =  bundlerSettings
// creates single js resource file for easy integration in html page
skip in packageJSDependencies := true
//persistLauncher in Test := false
//scalaJSUseMainModuleInitializer in Compile := true
//requiresDOM in Test := true
// copy  javascript files to js folder,that are generated using fastOptJS/fullOptJS

crossTarget in (Compile, fullOptJS) := file("js")
crossTarget in (Compile, fastOptJS) := file("js")
crossTarget in (Compile, packageJSDependencies) := file("js")
crossTarget in (Compile, scalaJSUseMainModuleInitializer) := file("js")
crossTarget in (Compile, packageMinifiedJSDependencies) := file("js")
artifactPath in (Compile, fastOptJS) := ((crossTarget in (Compile, fastOptJS)).value /
  ((moduleName in fastOptJS).value + "-opt.js"))
scalacOptions += "-feature"


import com.lihaoyi.workbench.Plugin._

workbenchSettings
updateBrowsers :=  updateBrowsers.triggeredBy(fastOptJS in Compile)
refreshBrowsers:=  refreshBrowsers.triggeredBy(fastOptJS in Compile)
bootSnippet := "scalajsreact.template.ReactApp().main();"
