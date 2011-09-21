package code {
	package model {

		import scala.xml.{NodeSeq, Text}

		import net.liftweb.record.field._
		import net.liftweb.mongodb._
		import net.liftweb.mongodb.record._
		import net.liftweb.mongodb.record.field._
		
		case class Social(name: String, id: String) extends JsonObject[Social] { 
				def meta = Social 
		} 

		object Social extends JsonObjectMeta[Social]
		
		class FriendDoc private() extends MongoRecord[FriendDoc] with ObjectIdPk[FriendDoc] {
			def meta = FriendDoc

			object lastname extends StringField(this, 20)
			object firstname extends StringField(this, 20)
			object photo extends StringField(this, 20)
			object desc extends StringField(this, 100)
			object email extends StringField(this, 20)
			object adress extends StringField(this, 20)
			object town extends StringField(this, 20)
			object phone extends StringField(this, 20)
			object urlPerso extends StringField(this, 20)
			object socials extends MongoJsonObjectListField (this, Social)
		}

		object FriendDoc extends FriendDoc with MongoMetaRecord[FriendDoc]
	}
}
