package ko.html.tests

import org.junit.Test
import ko.html.Html
import ko.html.Body
import ko.html.Head
import ko.html.Meta
import org.junit.Assert
import ko.html.Title
import ko.html.TextElement
import ko.html.Link

class HtmlTest {
    Test fun renderHtml() {
        val htmlTag = Html()
        Assert.assertEquals("<!DOCTYPE html><html></html>", htmlTag.toString())
    }

    Test fun langAttribute() {
        Assert.assertEquals("<!DOCTYPE html><html lang=\"en\"></html>", Html(lang = "en").toString())
    }

    Test fun head() {
        val html = Html {
            head()
        }
        Assert.assertEquals("<!DOCTYPE html><html><head></head></html>", html.toString())
    }

    Test fun headMeta() {
        val html = Html {
            head {
                meta()
            }
        }
        Assert.assertEquals("<!DOCTYPE html><html><head><meta></head></html>", html.toString())
    }
}

class BodyTest {
    Test fun renderHtml() {
        val body = Body()
        Assert.assertEquals("<body></body>", body.toString())
    }
}

class HeadTest() {
    Test fun renderHead() {
        val head = Head()
        Assert.assertEquals("<head></head>", head.toString())
    }

    Test fun title() {
        val head = Head {
            title("untitled")
        }
        Assert.assertEquals("<head><title>untitled</title></head>", head.toString())
    }
}

class MetaTest() {
    Test fun render() {
        val meta = Meta()
        Assert.assertEquals("<meta>", meta.toString())
    }

    Test fun charset() {
        val meta = Meta(charset = "utf-8")
        Assert.assertEquals("<meta charset=\"utf-8\">", meta.toString())
    }

    Test fun httpEquivContent() {
        val meta = Meta(httpEquiv = "X-UA-Compatible", content = "IE=edge")
        Assert.assertEquals("<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\">", meta.toString())
    }

    Test fun name() {
        val meta = Meta(name = "viewport", content = "width=device-width, initial-scale=1")
        Assert.assertEquals(
                "<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">", meta.toString()
        )
    }
}

class TitleTest {
    Test fun render() {
        val title = Title()
        Assert.assertEquals("<title></title>", title.toString())
    }

    Test fun text() {
        val title = Title("Kotlin Template")
        Assert.assertEquals("<title>Kotlin Template</title>", title.toString())
    }
}

class LinkTest {
    Test fun render() {
        val link = Link()
        Assert.assertEquals("<link>", link.toString())
    }

    Test fun linkStyleSheet() {
        val link = Link(href = "css/bootstrap.min.css", rel = "stylesheet")
        Assert.assertEquals("<link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">", link.toString())
    }
}

class TextElementTest {
    Test fun render() {
        val textElement = TextElement("<>&")
        Assert.assertEquals("&lt;&gt;&amp;", textElement.toString())
    }
}