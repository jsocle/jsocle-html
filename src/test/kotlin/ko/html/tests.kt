package ko.html

import org.junit.Test
import kotlin.test.assertEquals

class HtmlTest {
    Test fun renderHtml() {
        val htmlTag = Html()
        assertEquals("<!DOCTYPE html><html></html>", htmlTag.toString())
    }
}

class BodyTest {
    Test fun renderHtml() {
        val body = Body()
        assertEquals("<body></body>", body.toString())
    }
}
