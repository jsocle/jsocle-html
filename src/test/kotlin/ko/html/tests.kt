package ko.html

import org.junit.Test
import kotlin.test.assertEquals

class HtmlTest {
    Test fun renderHtml() {
        val htmlTag = html()
        assertEquals("<html></html>", htmlTag.toString())
    }
}
