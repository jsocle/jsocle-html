package ko.html

import org.junit.Test
import kotlin.test.assertEquals

class HtmlTest {
    Test fun renderHtml() {
        val htmlTag = Html()
        assertEquals("<!DOCTYPE html><html></html>", htmlTag.toString())
    }

    Test fun langAttribute() {
        assertEquals("<!DOCTYPE html><html lang=\"en\"></html>", Html(lang = "en").toString())
    }

    Test fun head() {
        val html = Html {
            head()
        }
        assertEquals("<!DOCTYPE html><html><head></head></html>", html.toString())
    }
}

class BodyTest {
    Test fun renderHtml() {
        val body = Body()
        assertEquals("<body></body>", body.toString())
    }
}

class HeadTest() {
    Test fun renderHead() {
        val head = Head()
        assertEquals("<head></head>", head.toString())
    }
}
