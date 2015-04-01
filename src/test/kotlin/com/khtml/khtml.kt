package com.khtml

import com.khtml.elements.Div
import com.khtml.elements.Img
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
    fun testText() {
        Assert.assertEquals("<div>text</div>", Div(text_ = "text").toString())
    }

    Test
    fun testSingleNode() {
        Assert.assertEquals("<img src=\"icon.png\">", Img(src = "icon.png").toString())
    }
}
