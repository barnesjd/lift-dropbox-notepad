package code.service

import net.liftweb._
import http.S
import common._

import com.dropbox.core._

import scala.collection.JavaConverters._

import java.io._

class DropboxService extends Loggable {
  private val fname = "notes.txt"
  private val absfname = "/"+fname
  private val utf8 = "UTF-8"
  
  private val client = { token:String => 
    new DbxClient(new DbxRequestConfig("lift-db-notepad", S.locale.toString), token)
  }
  
  def readNotes(token:String):Box[String] = {
    try {
      val c = client(token)
      val listing = c.getMetadataWithChildren("/")
      val file = listing.children.asScala.find(_.name == fname)
      
      file.map { f =>
        val s = new ByteArrayOutputStream
        c.getFile(absfname, null, s)
        new String(s.toByteArray(), utf8)
      }
    } catch {
      case e:Exception => Failure(e.getMessage)
    }   
  }
    
  def writeNotes(token:String, notes:String):Option[String] = {
    try {
      val c = client(token)
      c.uploadFile(absfname, DbxWriteMode.force, notes.length, new ByteArrayInputStream(notes.getBytes(utf8)))
      None
    } catch {
      case e:Exception => Some(e.getMessage())
    }
  }
}