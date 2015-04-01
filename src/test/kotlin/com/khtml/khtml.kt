package com.khtml

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
}

