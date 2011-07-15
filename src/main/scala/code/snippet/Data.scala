package code 
package snippet 

import net.liftweb.http.js.JsCmds.Alert
import net.liftweb._
import http._
import common._
import SHtml._
import util.Helpers._
import scala.xml.NodeSeq

import code.lib._

object Data {
  private object user extends RequestVar("")

  def process() = {
   

    // validate the user credentials and do a bunch of other stuff
	Alert(user + " process ! well done!")
  }

  /**
   * This is the part of the snippet that creates the form elements and connects the client side components to
   * server side handlers.
   *
   * @param xhtml - the raw HTML that we are going to be manipulating.
   * @return NodeSeq - the fully rendered HTML
   */
  def data(xhtml: NodeSeq): NodeSeq = {
    
    SHtml.ajaxForm(
      bind("data", xhtml,
           "user" -> SHtml.text(user.is, user(_), "maxlength" -> "20"),
           "submit" -> (SHtml.hidden(process) ++ <input type="submit" value="Submit"/>)))
  }
}
