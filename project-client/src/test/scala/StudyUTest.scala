import org.scalajs.jquery.jQuery
import shared.SharedMessages
import utest._

object StudyUTest extends TestSuite {

	setupUI()

	def setupUI(): Unit = {
		jQuery("body").append(s"<p>${SharedMessages.itWorks}</p>")

		jQuery("""<button id="click-me-button" type="button">Click me!</button>""")
				.click(() => jQuery("#print-debug").append(s"<p>You clicked the button!</p>"))
				.appendTo(jQuery("body"))

		jQuery("body").append("""<div id="print-debug"></div>""")
	}

	def tests = TestSuite {
		'HelloWorld {
			assert(jQuery(s"p:contains('${SharedMessages.itWorks}')").length == 1)
		}

		'ButtonClick {
			def messageCount =
				jQuery("p:contains('You clicked the button!')").length

			val button = jQuery("button:contains('Click me!')")
			assert(button.length == 1)
			assert(messageCount == 0)

			for (c <- 1 to 5) {
				button.click()
				assert(messageCount == c)
			}
		}
	}

}
