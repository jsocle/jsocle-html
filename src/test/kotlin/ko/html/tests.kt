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
import ko.html.Script
import ko.html.H1
import ko.html.Div

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

    Test fun body() {
        val html = Html {
            body {
            }
        }

        Assert.assertEquals("<!DOCTYPE html><html><body></body></html>", html.toString())
    }
}

class BodyTest {
    Test fun renderHtml() {
        val body = Body()
        Assert.assertEquals("<body></body>", body.toString())
    }

    Test fun script() {
        val body = Body {
            script(src = "js/bootstrap.min.js")
        }

        Assert.assertEquals("<body><script src=\"js/bootstrap.min.js\"></script></body>", body.toString())
    }

    Test fun h1() {
        val body = Body {
            h1(text = "Hello, world!")
        }

        Assert.assertEquals("<body><h1>Hello, world!</h1></body>", body.toString())
    }
}

class HeadTest() {
    Test fun renderHead() {
        val head = Head()
        Assert.assertEquals("<head></head>", head.toString())
    }

    Test fun title() {
        val head = Head {
            title(text = "untitled")
        }
        Assert.assertEquals("<head><title>untitled</title></head>", head.toString())
    }

    Test fun link() {
        val head = Head {
            link(href = "css/bootstrap.min.css", rel = "stylesheet")
        }
        Assert.assertEquals("<head><link href=\"css/bootstrap.min.css\" rel=\"stylesheet\"></head>", head.toString())
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


class ScriptTest {
    Test fun render() {
        val script = Script()
        Assert.assertEquals("<script></script>", script.toString())
    }

    Test fun source() {
        val script = Script(src = "js/bootstrap.min.js")
        Assert.assertEquals("<script src=\"js/bootstrap.min.js\"></script>", script.toString())
    }
}

class H1Test {
    Test fun render() {
        val h1 = H1(text = "Hello, world!")
        Assert.assertEquals("<h1>Hello, world!</h1>", h1.toString())
    }
}

class DivTest {
    Test fun render() {
        val div = Div()
        Assert.assertEquals("<div></div>", div.toString())
    }

    Test fun id() {
        val div = Div(id = "id")
        Assert.assertEquals("<div id=\"id\"></div>", div.toString())
    }

    Test fun class_() {
        val div = Div(class_ = "class1 class2")
        Assert.assertEquals("<div class=\"class1 class2\"></div>", div.toString())
    }
}
