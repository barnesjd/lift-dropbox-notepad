package code.service

import net.liftweb._
import common._

class DropboxService extends Loggable {
  def notes:String = "stub"
  def notes(n:String) = {
    logger.info(s"Saving '$n'")
  }
}