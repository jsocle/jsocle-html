package com.khtml.extentions

import com.khtml.BaseEmptyElement

public val BaseEmptyElement.classes: List<String>
    get() {
        val class_ = attributes["class_"]
        if (class_ == null) {
            return listOf()
        }
        return class_.split(' ').asList()
    }

public fun <T : BaseEmptyElement> T.addClass(vararg classes: String): T {
    var filtered = classes.filter { it !in this.classes }
    if (filtered.isNotEmpty()) {
        attributes["class_"] = (this.classes + filtered).joinToString(" ")
    }
    return this
}
