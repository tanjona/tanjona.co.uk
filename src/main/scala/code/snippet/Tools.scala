package code {
	package snippet {

		import _root_.scala.xml.{NodeSeq, Text}
		import _root_.net.liftweb.util._
		import _root_.net.liftweb.common._
		import _root_.net.liftweb.sitemap.Loc
		import _root_.net.liftweb.http._
		import _root_.java.util.Date
		import _root_.java.text.SimpleDateFormat
		import _root_.java.util.Locale
		import Helpers._
		import lib._
		
		object Tools {
			lazy val formatString = "d MMMM yyyy"
			val crawl = new NaturalCrawl
			val MapResult = crawl.weatherGoogle("London")
			
			private def FtoC(Tf:Int) = (Tf-32) * 5 / 9
			
			def DateNow = "#time *" #> (new SimpleDateFormat(formatString, Locale.ENGLISH) format new Date())
			
			def Weather = 
				".weather-icon [src]" #> ("http://www.google.com/" + MapResult("icon")) &
				 ".temp-high *" #> (FtoC(MapResult("high").toInt)+ "°C") &
				 ".temp-low *" #> (FtoC(MapResult("low").toInt) + "°C")
			
			def breadcrumb = "*" #> {
				val breadcrumbs: List[Loc[_]] =
					for {
						currentLoc <- S.location.toList
						loc <- currentLoc.breadCrumbs
					} yield loc
				"li *" #> breadcrumbs.map{
					loc => ".link *" #> loc.title &
						".link [href]" #> loc.createDefaultLink.getOrElse(Text("#"))
				}
			}
			
			
			
		}

	}
}
