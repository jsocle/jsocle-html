package com.github.jsocle.html

import com.github.jsocle.html.elements.*
import com.github.jsocle.html.extentions.addClass
import org.junit.Assert
import org.junit.Test

class KHtmlTest {
    Test
    fun testClass() {
        Assert.assertEquals("<div class=\"class\"></div>", Div(class_ = "class").toString())

        val property = Div()
        property.class_ = "class"
        Assert.assertEquals("<div class=\"class\"></div>", property.toString())

        val attribute = Div()
        attribute.attributes["class"] = "class"
        Assert.assertEquals("<div class=\"class\"></div>", attribute.toString())
    }

    Test
    fun testAddClass() {
        val div = Div()
        Assert.assertEquals("<div></div>", div.toString())

        div.addClass("first")
        Assert.assertEquals("<div class=\"first\"></div>", div.toString())

        div.addClass("second")
        Assert.assertEquals("<div class=\"first second\"></div>", div.toString())
        div.addClass("second")
        Assert.assertEquals("<div class=\"first second\"></div>", div.toString())

        div.addClass("third", "fourth")
        Assert.assertEquals("<div class=\"first second third fourth\"></div>", div.toString())
    }

    Test
    fun testText() {
        Assert.assertEquals("<div>text</div>", Div(text_ = "text").toString())
    }

    Test
    fun testTextEscape() {
        Assert.assertEquals(
                "<div>&lt;script&gt;alert(&quot;&amp;&quot;);&lt;/script&gt;</div>",
                Div(text_ = "<script>alert(\"&\");</script>").toString()
        )
    }

    Test
    fun testSingleNode() {
        Assert.assertEquals("<img src=\"icon.png\">", Img(src = "icon.png").toString())
    }

    Test
    fun testHtml() {
        val html = Html {
            body {
                div(class_ = "container") {
                    h1("title")
                }
            }
        }
        Assert.assertEquals(
                "<!DOCTYPE html><html><body><div class=\"container\"><h1>title</h1></div></body></html>", html.toString()
        )
    }

    Test
    fun testPlus() {
        val div = Div() {
            +"inline text"
            +P("child")
        }
        Assert.assertEquals("<div>inline text<p>child</p></div>", div.toString())
    }

    Test
    fun testData() {
        val div = Div() {
            data_["userId"] = "anonymous"
            data_["userName"] = "John Doe"
        }
        Assert.assertEquals("<div data-user-id=\"anonymous\" data-user-name=\"John Doe\"></div>", div.toString())
    }

    Test
    fun testHyphens() {
        Assert.assertEquals("foo", "foo".hyphens());
        Assert.assertEquals("foo-bar", "fooBar".hyphens());
    }

    Test
    fun testEquals() {
        Assert.assertEquals(Input(), Input())
        Assert.assertNotEquals(Input(), Div())
        Assert.assertEquals(Input(name = "name", type = "text"), Input(type = "text", name = "name"))
        Assert.assertNotEquals(Input(name = "name", type = "text"), Input(class_ = "_div", type = "text", name = "name"))
    }
}
