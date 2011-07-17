package code {
	package snippet {

		import _root_.scala.xml.{NodeSeq, Text}
		import _root_.net.liftweb.util._
		import _root_.net.liftweb.common._
		import com.foursquare.rogue.Rogue._
		import Helpers._
		import model._
		
		class Friends {
			def tanjonainfos (xhtml: NodeSeq) : NodeSeq = 
			{		
					val tanjonaDoc = FriendDoc where (_.firstname eqs "Tanjona") fetch()
					tanjonaDoc.flatMap (d => 
						d.socials.value.flatMap(
								p => bind("friend", xhtml,
						  			  "title" -> p.name,
									  AttrBindParam("alt",p.name.toString,"alt"),
						   			  AttrBindParam("link",Props.get(p.name + ".url", "") + p.id.toString,"href"),
						 			  AttrBindParam("img",Props.get("social.folder", "")+ p.name.toString + Props.get(p.name +".image.format", ".jpg"), "src"))			
						)
					)
			}
			
			def stickerNews = {
				val newsDoc =  CvObjectDoc where (_.category eqs "sticker") fetch()
				".list-item *" #> newsDoc.flatMap{
					d =>  d.activities.value.map { 
						s =>".link *" #> s.content
					}
				}
			}
		}
	}
}