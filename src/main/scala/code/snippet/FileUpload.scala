package code 
package snippet 

import scala.xml.{NodeSeq, Text, Elem}
import net.liftweb.http.{FileParamHolder, SHtml}
import SHtml._
import net.liftweb.util._
import net.liftweb.http.js.JsCmds.Alert
import net.liftweb.http.js._
import net.liftweb.common._
import java.util.Date
import code.lib._
import Helpers._
// source : https://github.com/Shadowfiend/lift-ajax-file-upload-example
class FileUpload {
	def fileUploadForm(form:NodeSeq) : NodeSeq = {
		var fileHolder: Box[FileParamHolder] = Empty
	 	val crawl = new NaturalCrawl
		def handleFile() = {
			// Do something with the file.
			printBox(fileHolder)
			val authorizedList = List("text/plain")
			fileHolder.map { holder => {
				    (holder.length, holder.mimeType) match {
					 case (length, _) if length > 50000 => 	Alert("file too large :" + length)
					 case (_, mime) if !authorizedList.contains(mime) => 	Alert("type file not supported :" + mime)
					 case (_,_) =>
					 	val lines = scala.io.Source.fromInputStream(holder.fileStream).mkString
						val blockLines = lines.split("\\n")
						val result = blockLines.map(n => crawl.getFollowers(n))
						//Alert( result.reduceLeft[(String)]( (l , r) => l + "\n" + r))
						val nodeList = result.map( elem => <li> {elem} </li>) 
						
						JsCmds.SetHtml("followers", <ul class="followers"> {nodeList}</ul>)
						
					}
				}
			} openOr {
				Alert("Well *that* upload failed...")
			}
		}

		val bindForm =
		"type=file" #> fileUpload((fph) => fileHolder = Full(fph)) &
		"type=submit" #> ajaxSubmit("Submit", handleFile _)

		ajaxForm(
		bindForm(form)
		)
	}

	def printRed(thing:Any) = {
		println("\u001b[0m\u001b[31m" + thing + "\u001b[0m")
	}
	def printGreen(thing:Any) = {
		println("\u001b[0m\u001b[32m" + thing + "\u001b[0m")
	}
	def printYellow(thing:Any) = {
		println("\u001b[0m\u001b[33m" + thing + "\u001b[0m")
	}
	def printBox[T](thing:Box[T]) = {
		import net.liftweb.common.{Full, Failure, Empty}
		thing match {
			case Full(_) =>
			printGreen(thing)
			case Failure(_, _, _) =>
			printYellow(thing)
			case Empty =>
			printRed(thing)
		}
	}
}