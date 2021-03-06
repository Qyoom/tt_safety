package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Location (
    id: 	    Pk[Long] = NotAssigned,
    city:		Option[String],
    country:	Option[String],
    addr1:		Option[String],
    addr2:		Option[String],
    addr3:		Option[String],
    postalCode: Option[String],
    lat:		String,
    lon:		String,
    alt:		Option[Int],
    desc:		Option[String],
    url:		Option[String]
)

object Location {
	
	// -- Parsers
  
	/**
	 * Parse a Location from a ResultSet
	 */
	val simple = {
		get[Pk[Long]]("location.id") ~
		get[Option[String]]("location.city") ~
		get[Option[String]]("location.country") ~
		get[Option[String]]("location.address_1") ~
		get[Option[String]]("location.address_2") ~
		get[Option[String]]("location.address_3") ~
		get[Option[String]]("location.postal_code") ~
		get[String]("location.latitude") ~
		get[String]("location.longitude") ~
		get[Option[Int]]("location.altitude") ~
		get[Option[String]]("location.desc") ~
		get[Option[String]]("location.url") map {
		  	case id ~ city ~ country ~ addr1 ~ addr2 ~ addr3 ~ postalCode ~ lat ~ lon ~ alt ~ desc ~ url =>
		  	  	Location(id, city, country, addr1, addr2, addr3, postalCode, lat, lon, alt, desc, url)
		}
	}
	
	// Read - yields single location per id
	def single(id: Long): Option[Location] = DB.withConnection { implicit c =>
	    SQL("select * from location where id = {id}").on(
	        'id -> id
	    ).as(Location.simple.singleOpt)
	}
}




