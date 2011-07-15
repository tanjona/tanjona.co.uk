package code {
	package snippet {

		import _root_.scala.xml.{NodeSeq, Text}
		import _root_.net.liftweb.util._
		import _root_.net.liftweb.common._
		import com.foursquare.rogue.Rogue._
		import Helpers._
		import model._
		
		class Experiencecv {
		
			def experiences(xhtml: NodeSeq): NodeSeq = 
			{			
				val experienceDocs = CvObjectDoc where (_.category eqs "experience") fetch()
				
				Thread.sleep(randomLong(5 seconds))
				
			    experienceDocs.flatMap((exp: CvObjectDoc) => bind ("e", xhtml, 
			           "title" -> exp.title, 
			           "activities" -> exp.activities.value.flatMap((act:Activity) => bindActivities(act))
					)
				)	
			}
			
			
			def staticheader(xhtml: NodeSeq): NodeSeq = {		
				Thread.sleep(randomLong(5 seconds))
				bind ("e", xhtml, "static" ->
						<li class="pixie green">
							<div class="caption">
								<h3>
									<a href="#" class="link-text">Tanjona Rafidison	</a>
								</h3>
								<p class="strap">
									<a href="#" class="link-text">Looking for a Software developer Position</a>
								</p>
							</div>
							<div class="trail-text" style="display: none; ">
								<a href="#" class="link-text">  Try<strong> me</strong></a>
							</div>
							<a href="#" class="link-image ">
								<img src="/images/tj.jpg" width="460" height="356" alt="Tanjona Julien" class=""/>
							</a>
						</li> 
					)
			}
			
			def bindActivities(act:Activity): NodeSeq =
				<li class="bullet">
					<a href="#" class="link-text"> {act.content }</a> <br/> <b>{act.tools}</b>
				</li>
			
			
			def educations(xhtml: NodeSeq): NodeSeq = 
			{
				val educationDocs = CvObjectDoc  where (_.category eqs "education") fetch ()
				
				Thread.sleep(randomLong(5 seconds))
				
				educationDocs.flatMap((exp: CvObjectDoc) => bind ("e", xhtml, 
			           "title" -> exp.title,
						AttrBindParam("link",exp.websiteCompany.toString,"href"),
			 		   "image" -> <img src={exp.pictureCompany.toString} width="140" height="84"></img>,
			           "activities" -> exp.activities.value.flatMap((act:Activity) => bindActivities(act))
					)
				)
			}
		}
	}
}