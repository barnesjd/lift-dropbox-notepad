package code.comet

import net.liftweb._
import http._
import common._
import util._
import Helpers._
import js._
import JsCmds._
import JE._

class DropboxActor extends CometActor {
  override def lowPriority = {
    case notes:String => partialUpdate(
      SetValueAndFocus("notes", notes) &
      JsRaw("document.getElementById('notes').disabled=false;")
    )
  }
  
  def render = PassThru
}