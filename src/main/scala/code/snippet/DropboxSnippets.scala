package code
package snippet

import service.DropboxService
import lib.DependencyFactory

import net.liftweb._
import common._
import util._
import Helpers._
import http._
import SHtml._
import js._
import JsCmds._
import JE._
import js.jquery.JqJE._

import omniauth.Omniauth._

class DropboxSnippets extends Loggable {
  lazy val dropbox:Box[DropboxService] = DependencyFactory.inject[DropboxService]
  
  def signin = currentAuth.map(a => ClearNodes).openOr(PassThru)
  
  def notes = currentAuth.map { a =>
    val user = a.name
    ".notes *" #> s"Hello $user! Take some notes!" &
    ".save [onclick]" #> ajaxCall(
	  Jq(".notes") ~> JsFunc("val"), 
	  { str => 
	    dropbox map (_.notes(str))
	    Noop 
	  }
	)
    
  }.openOr(ClearNodes)
}