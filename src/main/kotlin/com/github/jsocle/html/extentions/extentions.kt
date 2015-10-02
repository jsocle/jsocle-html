package com.github.jsocle.html.extentions

import com.github.jsocle.html.BaseEmptyElement

public val BaseEmptyElement.classes: List<String>
    get() {
        val class_ = attributes["class_"] ?: return listOf()
        return class_.split(' ').toList()
    }

public fun <T : BaseEmptyElement> T.addClass(vararg classes: String): T {
    var filtered = classes.filter { it !in this.classes }
    if (filtered.isNotEmpty()) {
        attributes["class_"] = (this.classes + filtered).joinToString(" ")
    }
    return this
}
