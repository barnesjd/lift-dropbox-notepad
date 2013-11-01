package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._
import mapper._
import net.liftweb.squerylrecord.RecordTypeMode._

import code.snippet._

//import org.hoisted.lib._
//import java.io._

import net.liftmodules.{FoBo}

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot extends Loggable {
  def boot {
    
    // where to search snippet
    LiftRules.addToPackages("code")
    
    //The FoBo setup and init
    FoBo.InitParam.JQuery=FoBo.JQuery182  
    FoBo.InitParam.ToolKit=FoBo.Bootstrap231
    FoBo.InitParam.ToolKit=FoBo.FontAwesome300
    FoBo.InitParam.ToolKit=FoBo.PrettifyJun2011
    FoBo.init()

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    //LiftRules.setSiteMapFunc(() => sitemap /*sitemapMutators(sitemap)*/ )
    LiftRules.setSiteMap(Paths.sitemap)
        
    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // What is the function to test if a user is logged in?
    //LiftRules.loggedInTest = Full(() => User.loggedIn_?)

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))
  }
}

object Paths {
  //import xml.NodeSeq
  import scala.xml._
  
  val index      = Menu.i("Home") / "index"
  
  def sitemap = SiteMap(index >> LocGroup("topLeft","nl1"))
}
