package code {
	package webservice {

		import net.liftweb.http._
		import net.liftweb.http.rest._
		import net.liftweb.json.JsonAST._
		import net.liftweb.json.JsonDSL._
		import net.liftweb.json.JsonDSL.{pair2Assoc, pair2jvalue}
		import net.liftweb.json.Printer._
		import model._
		
		object FriendsRESTService extends RestHelper {
			val friendDocs = FriendDoc.findAll
		    val jsonFriend = friendDocs.map { res => 
				 ("firstname" -> res.firstname.toString) ~
				 ("lastname" -> res.lastname.toString) ~
				 ("adress" -> res.adress.toString) ~
				 ("desc" -> res.desc.toString) ~
				("photo" -> res.photo.toString) ~ 
				("email" -> res.email.toString) ~
				("url" -> res.urlPerso.toString) ~
				("phone" -> res.phone.toString) ~
				("socials" -> 
					res.socials.value.map { s => 
						(("name" -> s.name.toString) ~
						 ("id" -> s.id.toString))})
			}
			serve {
				case "api" :: "user" :: "all" :: _ JsonGet _ =>  JArray(jsonFriend)			
			}
		}
	}
}
