package com.github.jsocle.html

import com.github.jsocle.html.elements.Html
import java.util.ArrayList
import kotlin.properties.ReadWriteProperty

fun String.hyphens(): String {
    val builder = StringBuilder()
    for (ch in this) {
        if (ch.isUpperCase()) {
            builder.append('-')
            builder.append(ch.toLowerCase())
        } else {
            builder.append(ch)
        }
    }
    return builder.toString()
}

object attributeHandler : ReadWriteProperty<BaseEmptyElement, String?> {
    operator override fun get(thisRef: BaseEmptyElement, property: PropertyMetadata): String? {
        val name = property.name.hyphens()
        if (thisRef.attributes.containsKey(name)) {
            return thisRef.attributes[name]
        }
        return null
    }

    operator override fun set(thisRef: BaseEmptyElement, property: PropertyMetadata, value: String?) {
        val name = property.name.hyphens()
        if (value != null) {
            thisRef.attributes.put(name, value)
        } else {
            if (thisRef.attributes.containsKey(name)) {
                thisRef.attributes.remove(name)
            }
        }
    }
}

class DataHandler(private val element: BaseEmptyElement) {
    operator fun get(name: String): String? {
        val attr = "data-${name.hyphens()}"
        if (element.attributes.containsKey(attr)) {
            return element.attributes[attr]
        }
        return null
    }

    operator fun set(name: String, value: String?) {
        val attr = "data-${name.hyphens()}"
        if (value != null) {
            element.attributes.put(attr, value)
        } else {
            if (element.attributes.containsKey(attr)) {
                element.attributes.remove(attr)
            }
        }
    }
}


abstract class Node {
    abstract public fun render(builder: Appendable)

    override public fun toString(): String {
        val builder = StringBuilder()
        render(builder)
        return builder.toString()
    }
}

abstract class BaseEmptyElement(public val elementName: String) : Node() {
    public val attributes: MutableMap<String, String> = hashMapOf()
    public val data_: DataHandler = DataHandler(this)

    override public fun render(builder: Appendable) {
        if (this is Html) {
            builder.append("<!DOCTYPE html>")
        }
        renderOpen(builder)
    }

    private fun renderOpen(builder: Appendable) {
        builder.append("<").append(elementName)
        attributes
                .map {
                    val key = if (it.getKey().endsWith('_')) {
                        it.getKey().substring(0, it.getKey().lastIndex);
                    } else {
                        it.getKey()
                    }
                    key to it.getValue()
                }
                .sortedBy { it.first }
                .forEach {
                    val (name, value) = it
                    builder.append(" ").append(name).append("=\"").append(value).append("\"")
                }
        builder.append(">")
    }

    override fun equals(other: Any?): Boolean {
        if (other is BaseEmptyElement) {
            return elementName == other.elementName && attributes == other.attributes;
        }
        return false;
    }
}

abstract class BaseElement(elementName: String, text_: String? = null) : BaseEmptyElement(elementName = elementName) {
    public val children: List<Node> = arrayListOf()

    init {
        if (text_ != null) {
            addNode(TextNode(text_))
        }
    }

    override public fun render(builder: Appendable) {
        super.render(builder)
        for (child in children) {
            child.render(builder)
        }
        builder.append("</").append(elementName).append(">")
    }

    public fun addNode(node: Node) {
        (children as ArrayList<Node>).add(node)
    }

    public fun addNode(text: String) {
        addNode(TextNode(text))
    }

    operator public fun Node.plus() {
        addNode(this)
    }

    operator public fun String.plus() {
        addNode(this)
    }

    override fun equals(other: Any?): Boolean {
        if (super.equals(other)) {
            if (other is BaseElement) {
                return children == other.children
            }
        }
        return false;
    }
}

class TextNode(public val text: String) : Node() {
    override fun render(builder: Appendable) {
        for (c in text) {
            builder.append(when (c) {
                '"' -> "&quot;"
                '<' -> "&lt;"
                '>' -> "&gt;"
                '&' -> "&amp;"
                else -> "$c"
            })
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other is TextNode) {
            return text == other.text
        }
        return false
    }
}
