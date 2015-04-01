package com.khtml

import com.google.common.base.CaseFormat
import java.util.ArrayList
import kotlin.properties.ReadWriteProperty

fun String.hyphens(): String {
    return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, this)
}

object attributeHandler : ReadWriteProperty<BaseEmptyElement, String?> {
    override fun get(thisRef: BaseEmptyElement, desc: PropertyMetadata): String? {
        val name = desc.name.hyphens()
        if (thisRef.attributes.containsKey(name)) {
            return thisRef.attributes[name]
        }
        return null
    }

    override fun set(thisRef: BaseEmptyElement, desc: PropertyMetadata, value: String?) {
        val name = desc.name.hyphens()
        if (value != null) {
            thisRef.attributes.put(name, value)
        } else {
            if (thisRef.attributes.containsKey(name)) {
                thisRef.attributes.remove(name)
            }
        }
    }
}

abstract class Node {
    abstract fun render(builder: Appendable)

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder)
        return builder.toString()
    }
}

abstract class BaseEmptyElement(public val elementName: String) : Node() {
    public val attributes: MutableMap<String, String> = hashMapOf()

    override fun render(builder: Appendable) {
        renderOpen(builder)
    }

    protected fun renderOpen(builder: Appendable) {
        builder.append("<").append(elementName)
        attributes
                .map {
                    it.getKey().replaceAll("[_]+$", "") to it.getValue()
                }
                .sortBy { it.first }
                .forEach {
                    val (name, value) = it
                    builder.append(" ").append(name).append("=\"").append(value).append("\"")
                }
        builder.append(">")
    }
}

abstract class BaseElement(elementName: String, text_: String? = null) : BaseEmptyElement(elementName = elementName) {
    public val children: List<Node> = arrayListOf()

    init {
        if (text_ != null) {
            (children as ArrayList<Node>).add(TextNode(text_))
        }
    }

    override fun render(builder: Appendable) {
        super.render(builder)
        for (child in children) {
            child.render(builder)
        }
        builder.append("</").append(elementName).append(">")
    }

}

class TextNode(public val text: String) : Node() {
    override fun render(builder: Appendable) {
        builder.append(text)
    }
}
