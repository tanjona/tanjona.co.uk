package code {
	package model {

		import net.liftweb._
		import net.liftweb.common.{Box,Full,Empty,Failure,ParamFailure}
		import util.Props
		import com.mongodb.{Mongo, ServerAddress}
		import net.liftweb.mongodb.{DefaultMongoIdentifier, MongoDB, MongoAddress, MongoHost}

		object MongoConfig {
			def init: Unit = {
				val srvr = new MongoHost(new ServerAddress(Props.get("mongodb.host", "localhost"), 10018))
				MongoDB.defineDbAuth(DefaultMongoIdentifier,
				new MongoAddress(srvr, Props.get("mongodb.name") openOr "test" ),
				Props.get("mongodb.user") openOr "" ,
				Props.get("mongodb.pass") openOr "")
			}
		}


	}
}