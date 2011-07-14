package code {
	package model {

		import scala.xml.{NodeSeq, Text}

		import net.liftweb.common._
		import net.liftweb.util._
		import net.liftweb.util.Helpers._
		import net.liftweb.record._
		import net.liftweb.record.field._
		import net.liftweb.mongodb._
		import net.liftweb.mongodb.record._
		import net.liftweb.mongodb.record.field._

		case class Activity(content: String, tools: String) extends JsonObject[Activity] { 
			def meta = Activity 
		} 

		object Activity extends JsonObjectMeta[Activity]


		class CvObjectDoc extends MongoRecord[CvObjectDoc] with MongoId[CvObjectDoc] {
			def meta = CvObjectDoc 

			object activities extends MongoJsonObjectListField (this, Activity)

			object title extends StringField (this, 50)
			object category extends StringField (this, 50)
			object layout extends StringField (this,50)
			
			
			object startingDate extends StringField (this,50)
			object endingDate extends StringField (this,50)
			
			object company  extends StringField (this, 50)
			object pictureCompany extends StringField (this, 100)
			object websiteCompany extends StringField (this, 100)
			
			object position extends StringField (this, 50)
			object user extends StringField (this, 50)
			object location extends StringField (this, 50)
			object types extends StringField(this,50)
		} 

		object CvObjectDoc extends CvObjectDoc with MongoMetaRecord[CvObjectDoc]
	}
}
