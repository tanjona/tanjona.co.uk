package code {
	package snippet {

		import _root_.scala.xml.{NodeSeq, Text}
		import _root_.net.liftweb.util._
		import _root_.net.liftweb.common._
		import com.foursquare.rogue.Rogue._
		import Helpers._
		import model._
		
		class About {
			def aboutinfos = 
			{		
					val aboutDoc = CvObjectDoc where (_.category eqs "aboutSite") fetch()
					".infosAbout *" #> aboutDoc.map(p =>
								"a [href]" #> p.pictureCompany.toString &
								"img [src]" #> p.layout.toString &
								".block *" #> p.activities.value.map (s =>
										"a [href]" #> s.tools &
										"p *" #>  s.content &
										"h2 *" #> p.title.toString &
										"small *" #> p.websiteCompany.toString
										) 
					 			)	
			}
		
		}
	}
}