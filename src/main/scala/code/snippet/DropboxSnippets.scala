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

import omniauth.Omniauth._

class DropboxSnippets extends Loggable {
  lazy val dropbox:Box[DropboxService] = DependencyFactory.inject[DropboxService]
  
  def signin = currentAuth.map(a => ClearNodes).openOr(PassThru)
  
  def notes = currentAuth.map { a =>
    val user = a.name
    val token = a.token
    val default = s"Hello $user! Take some notes!"
    
    S.session.map { s => 
      Schedule { () => 
        val notes = (dropbox flatMap (_.readNotes(token)) openOr (default))
        s.sendCometActorMessage("DropboxActor", Empty, notes)
      }
    }
    
    val notes = "#notes *" #> "loading..."
    
    val save = ".save [onclick]" #> ajaxCall(
	  ElemById("notes") ~> JsVal("value"), 
	  { notes => 
	    dropbox map (_.writeNotes(token, notes))
	    Noop 
	  }
	)
    
	notes & save
  }.openOr(ClearNodes)
}