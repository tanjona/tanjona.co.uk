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
				
				Thread.sleep(randomLong(2 seconds))
				
			    experienceDocs.flatMap((exp: CvObjectDoc) => bind ("e", xhtml, 
			           "title" -> exp.title,
			 		   "dates" -> <span class="date">{exp.startingDate.toString} - {exp.endingDate.toString} </span>,
			           "activities" -> exp.activities.value.flatMap((act:Activity) => bindActivities(act))
					)
				)	
			}
			
			
			def staticheader(xhtml: NodeSeq): NodeSeq = {		
				Thread.sleep(randomLong(2 seconds))
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
					<span class="link-text"> {act.content }</span> <br/> <b style="font-size:12px">{act.tools}</b>
				</li>
			
			
			def educations(xhtml: NodeSeq): NodeSeq = 
			{
				val educationDocs = CvObjectDoc  where (_.category eqs "education") fetch ()
				
				Thread.sleep(randomLong(2 seconds))
				
				educationDocs.flatMap((exp: CvObjectDoc) => bind ("e", xhtml, 
			           "title" -> exp.title,
						AttrBindParam("link",exp.websiteCompany.toString,"href"),
			 		   "image" -> <img src={exp.pictureCompany.toString} width="140" height="84"></img>,					
			           "activities" -> exp.activities.value.flatMap((act:Activity) => bindActivities(act))
					)
				)
			}
			
			def aboutme(xhtml: NodeSeq): NodeSeq =
			{
				val aboutMeDocs = CvObjectDoc  where (_.category eqs "aboutMe") fetch ()
					aboutMeDocs.flatMap((exp: CvObjectDoc) => bind ("e", xhtml, 
				           "title" -> exp.title,
							"desc" -> exp.layout,
				 		   "image" -> <img src={exp.pictureCompany.toString} width="140" height="130"></img>
						)
					)
			}
			
			def ilike(xhtml: NodeSeq): NodeSeq =
			{
				val ilikeDocs = CvObjectDoc  where (_.category eqs "like") fetch ()
					ilikeDocs.flatMap((exp: CvObjectDoc) => bind ("e", xhtml, 
				           "title" -> exp.title,
							"activities" -> exp.activities.value.flatMap((act:Activity) => bindActivitiesLike(act))
						)
					)
			}
			
			def bindActivitiesLike(act:Activity): NodeSeq =
				<li class="l1 news">
					<span class="link-text">{act.content}</span>
				</li>
			
		}
	}
}