package code.snippet

import net.liftweb._
import common._
import util._
import Helpers._

import omniauth.Omniauth._

class DropboxSnippets {
  def signin = currentAuth.map(a => ClearNodes).openOr(PassThru)
  
  def notes = currentAuth.map { a =>
    val user = a.name
    "textarea *" #> s"Hello $user! Take some notes!"
  }.openOr(ClearNodes)
}