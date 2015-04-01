package com.khtml

import com.google.common.base.CaseFormat
import java.util.ArrayList
import kotlin.properties.ReadWriteProperty

fun String.hyphens(): String {
    return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, this)
}

object attributeHandler : ReadWriteProperty<BaseElement, String?> {
    override fun get(thisRef: BaseElement, desc: PropertyMetadata): String? {
        val name = desc.name.hyphens()
        if (thisRef.attributes.containsKey(name)) {
            return thisRef.attributes[name]
        }
        return null
    }

    override fun set(thisRef: BaseElement, desc: PropertyMetadata, value: String?) {
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

abstract class  AbstractElement {
    abstract fun render(builder: Appendable)

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder)
        return builder.toString()
    }
}

abstract class BaseElement(public val elementName: String, text_: String? = null) : AbstractElement() {
    public val attributes: MutableMap<String, String> = hashMapOf()
    public val children: List<AbstractElement> = arrayListOf()

    init {
        if (text_ != null) {
            (children as ArrayList<AbstractElement>).add(TextElement(text_))
        }
    }

    override fun render(builder: Appendable) {
        renderOpen(builder)
        for (child in children) {
            child.render(builder)
        }
        builder.append("</").append(elementName).append(">")
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

class TextElement(public val text: String): AbstractElement() {
    override fun render(builder: Appendable) {
        builder.append(text)
    }
}
