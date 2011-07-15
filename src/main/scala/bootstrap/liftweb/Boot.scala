package bootstrap.liftweb



import java.util.Locale
import javax.mail.{Authenticator, PasswordAuthentication}
import net.liftweb.mongodb._
import net.liftweb.util._
import net.liftweb.common._
import net.liftweb.http._
import net.liftweb.http.provider._
import net.liftweb.sitemap._
import net.liftweb.sitemap.Loc._
import net.liftweb.mapper._
import Helpers._

import code.model._
import code.webservice._


/**
* A class that's instantiated early and run.  It allows the application
* to modify lift's environment
*/
class Boot extends Loggable  {
	def boot {
		MongoConfig.init
		LiftRules.addToPackages("code")
		
		/************************ FILE UPLOAD *******************************/
		// https://github.com/Shadowfiend/lift-ajax-file-upload-example
	    // In cases where we have an AJAX request for IE with an uploaded file, we
	    // assume we served through an iframe (a fairly safe assumption) and serve
	    // up the response with a content type of text/plain so that IE does not
	    // attempt to save the response as a downloaded file.
	    LiftRules.responseTransformers.append {
	      resp =>
	        (for (req <- S.request) yield {
	          resp match {
	            case InMemoryResponse(data, headers, cookies, code)
	                                    if ! req.uploadedFiles.isEmpty &&
	                                        req.isIE &&
	                                        req.path.wholePath.head == LiftRules.ajaxPath =>
	              val contentlessHeaders = headers.filterNot(_._1.toLowerCase == "content-type")
	              InMemoryResponse(data, ("Content-Type", "text/plain; charset=utf-8") :: contentlessHeaders, cookies, code)
	            case _ => resp
	          }
	        }) openOr resp
	    }
	    /********************************************************************/


		// Build SiteMap
		val entries = Menu(Loc("Home", List("index"), "home", LocGroup("home", "main-menu"))) ::
		Menu(Loc("Lift", List("lift"), "lift", LocGroup("lift", "main-menu"))) ::
		Menu(Loc("About", Link(List("about"), true,"about/index"), "About this web site", LocGroup("about", "main-menu"))) ::
		Menu(Loc("Data", List("data"), "data", LocGroup("data", "main-menu"))) ::
		Menu(Loc("Friends", Link(List("flex"), true, "/flex/index"), "Friends", LocGroup("friends", "main-menu")))::
		Nil

		//User.sitemap

		LiftRules.setSiteMap(SiteMap(entries:_*))

		//Show the spinny image when an Ajax call starts
		LiftRules.ajaxStart = Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

		// Make the spinny image go away when it ends
		LiftRules.ajaxEnd =
		Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

		// Force the request to be UTF-8
		LiftRules.early.append(makeUtf8)

		// What is the function to test if a user is logged in?
		LiftRules.loggedInTest = Full(() => User.loggedIn_?)

		LiftRules.localeCalculator = localeCalculator _

		configMailer
		// Use HTML5 for rendering
		LiftRules.htmlProperties.default.set((r: Req) => new Html5Properties(r.userAgent))

		//adding webservice
		LiftRules.statelessDispatchTable.append(FriendsRESTService)
		
		// Use jQuery 1.4
		LiftRules.jsArtifacts = net.liftweb.http.js.jquery.JQuery14Artifacts

	}

	/**
	* Force the request to be UTF-8
	*/
	private def makeUtf8(req: HTTPRequest) {
		req.setCharacterEncoding("UTF-8")
	}

	private def localeCalculator(request : Box[HTTPRequest]): Locale =
	User.currentUser.map(u => new Locale(u.locale.value)) openOr Locale.getDefault

	/*
	* Config mailer
	*/
	private def configMailer {

		var isAuth = Props.get("mail.smtp.auth", "false").toBoolean

		Mailer.customProperties = Props.get("mail.smtp.host", "localhost") match {
			case "smtp.gmail.com" =>
			isAuth = true
			Map(
			"mail.smtp.host"            -> "smtp.gmail.com",
			"mail.smtp.port"            -> "587",
			"mail.smtp.auth"            -> "true",
			"mail.smtp.starttls.enable" -> "true"
			)
			case h =>
			isAuth = true 
			Map(
			"mail.smtp.host"            -> h,
			"mail.smtp.port"            -> Props.get("mail.smtp.port", "587"),
			"mail.smtp.auth"            -> isAuth.toString
			)
		}

		if (isAuth) {
			(Props.get("mail.smtp.user"), Props.get("mail.smtp.pass")) match {
				case (Full(username), Full(password)) =>
				Mailer.authenticator = Full(new Authenticator() {
					override def getPasswordAuthentication = new
					PasswordAuthentication(username, password)
				})
				case _ => logger.error("Username/password not supplied for Mailer.")
			}
		}
	}
}
