import scala.scalajs.js.JSApp
import scalatags.Text.all._
import org.scalajs.dom
import org.scalajs.jquery.jQuery
import shared.SharedMessages

object JsApplication extends JSApp {

	def main(): Unit = {
		jQuery("body").append("<p>Hello World!</p>")
		jQuery("body").append(s"<p>${SharedMessages.itWorks}")
	}

}
