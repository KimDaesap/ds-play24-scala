package controllers

import com.google.inject.Inject
import org.webjars.RequireJS
import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.modules.reactivemongo._
import play.modules.reactivemongo.json._
import reactivemongo.api.FailoverStrategy
import reactivemongo.api.collections.bson.BSONCollection
import scala.concurrent.duration.FiniteDuration
import shared.SharedMessages

import scalatags.Text.all._

//class Application @Inject()(val reactiveMongoApi: ReactiveMongoApi)
//		extends Controller with MongoController with ReactiveMongoComponents {
//
//	def collection: BSONCollection = db.collection[BSONCollection](
//		"persons",
//		FailoverStrategy(FiniteDuration(5000, "ms"), 5, n => 1))
//
//	def index = Action {
//		Ok(views.html.index(null))
//	}
//
//	def create(name: String, age: Int) = Action.async {
//		val json = Json.obj(
//			"name" -> name,
//			"age" -> age,
//			"created" -> new java.util.Date().getTime())
//
//		collection.insert(json).map(lastError =>
//			Ok("Mongo LastError: %s".format(lastError)))
//	}
//
//	def test() = Action {
//		Ok("I am a boy")
//	}
//
//}

class Application @Inject()(val reactiveMongoApi: ReactiveMongoApi,
                            webJarAssets: WebJarAssets, requireJS: RequireJS)
		extends Controller with MongoController with ReactiveMongoComponents {

	def index = Action {
		Ok(views.html.index(SharedMessages.itWorks))
	}

	def collection: BSONCollection = db.collection[BSONCollection](
		"persons",
		FailoverStrategy(FiniteDuration(5000, "ms"), 5, n => 1))

	def create(name: String, age: Int) = Action.async {
		val json = Json.obj(
			"name" -> name,
			"age" -> age,
			"created" -> new java.util.Date().getTime())

		collection.insert(json).map(lastError =>
			Ok("Mongo LastError: %s".format(lastError)))
	}

	def scalatag = Action {
		Ok(
			html(
				head(
					script(src := "..."),
					script("alert('Hello World')")
				),
				body(
					div(
						h1(id := "title", "This is a title"),
						p("This is a big paragraph of text")
					)
				)
			).render
		).as("text/html")
	}

}
