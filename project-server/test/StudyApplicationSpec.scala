import java.util.Date

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Span, Seconds}
import org.scalatestplus.play.{OneAppPerTest, PlaySpec}
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.Play.current

import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument


class StudyApplicationSpec extends PlaySpec with OneAppPerTest with ScalaFutures {

	val logger = Logger(this.getClass.getName)

	lazy val reactiveMongoApi = current.injector.instanceOf[ReactiveMongoApi]
	lazy val db = reactiveMongoApi.connection("test")
	lazy val collection = db.collection[BSONCollection]("persons")

	/* --------------------------------------------------------------------------------- */

	"ReactiveMongo" should {

		"ReactiveMongoApi valid" in {
			assert(reactiveMongoApi != null, "ReactiveMongoApi instance not found!")
			assert(db != null, "Database invalid")

			val user = BSONDocument(
				"name" -> "Kim daesap",
				"age" -> 10,
				"created" -> new Date().getTime())

			whenReady(collection.insert(user), timeout(Span(10, Seconds))) {
				result => logger.debug(result.toString)
			}
		}

	}


}
