package ko.html

import kotlin.properties.ReadWriteProperty
import com.google.common.base.CaseFormat
import com.google.common.html.HtmlEscapers

fun String.hyphens(): String {
    return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, this)
}

object attributeHandler : ReadWriteProperty<AbstractElement, String?> {
    override fun get(thisRef: AbstractElement, desc: PropertyMetadata): String? {
        val name = desc.name.hyphens()
        if (thisRef.attributes.containsKey(name)) {
            return thisRef.attributes[name]
        }
        return null
    }

    override fun set(thisRef: AbstractElement, desc: PropertyMetadata, value: String?) {
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

abstract class AbstractElement(val elementName: String) {
    val attributes: MutableMap<String, String> = hashMapOf()

    abstract fun render(builder: StringBuilder)

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder)
        return builder.toString()
    }

    protected fun renderOpen(builder: StringBuilder) {
        builder.append("<").append(elementName)
        attributes.map { it }
                .sortBy { it.getKey() }
                .forEach {
                    builder.append(" ").append(it.getKey()).append("=\"").append(it.getValue()).append("\"")
                }
        builder.append(">")
    }
}

class TextElement(val text: String) : AbstractElement("TextElement") {
    override fun render(builder: StringBuilder) {
        builder.append(HtmlEscapers.htmlEscaper().escape(text))
    }
}

abstract class SingleElement(name: String) : AbstractElement(name) {
    override fun render(builder: StringBuilder) {
        renderOpen(builder)
    }
}

abstract class Element(name: String) : AbstractElement(name) {
    protected val children: MutableList<AbstractElement> = arrayListOf()

    override fun render(builder: StringBuilder) {
        renderOpen(builder)
        for (child in children) {
            child.render(builder)
        }
        builder.append("</").append(elementName).append(">")
    }
}

class Html(lang: String? = null, init: Html.() -> Unit = {}) : Element("html") {
    var lang by attributeHandler

    override fun render(builder: StringBuilder) {
        builder.append("<!DOCTYPE html>")
        super.render(builder)
    }

    {
        this.lang = lang
        init()
    }

    fun head(init: Head.() -> Unit = {}): Head {
        val element = Head(init = init)
        children.add(element)
        return element;
    }
}

class Head(init: Head.() -> Unit = {}) : Element("head") {
    {
        init()
    }

    fun meta(charset: String? = null, httpEquiv: String? = null, name: String? = null, content: String? = null, init: Meta.() -> Unit = {}): Meta {
        val element = Meta(charset = charset, httpEquiv = httpEquiv, name = name, content = content, init = init)
        children.add(element)
        return element
    }

    fun title(text: String? = null): Title {
        val title = Title(text)
        children.add(title)
        return title
    }
}

class Meta(charset: String? = null, httpEquiv: String? = null, name: String? = null, content: String? = null, init: Meta.() -> Unit = {}) : SingleElement("meta") {
    var charset by attributeHandler
    var httpEquiv by attributeHandler
    var name by attributeHandler
    var content by attributeHandler

    {
        this.charset = charset
        this.httpEquiv = httpEquiv
        this.name = name
        this.content = content
        init()
    }
}

class Title(text: String? = null) : Element("title") {
    {
        if (text != null) {
            children.add(TextElement(text))
        }
    }
}

class Link(href: String? = null, rel: String? = null) : SingleElement("link") {
    var href by attributeHandler
    var rel by attributeHandler

    {
        this.href = href
        this.rel = rel
    }
}

class Body : Element("body")
