package code {
	package webservice {

		import net.liftweb.http._
		import net.liftweb.http.rest._
		import net.liftweb.json.JsonAST._
		
		object FriendsRESTService extends RestHelper {
			serve {
				case "api" :: "user" :: id :: _ JsonGet _ => JString(id)
				
			}
		}
	}
}
