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

    abstract fun render(builder: Appendable)

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder)
        return builder.toString()
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

abstract class FindableElement(name: String, id: String?, class_: String?) : AbstractElement(elementName = name) {
    var id by attributeHandler
    var class_ by attributeHandler

    {
        this.id = id
        this.class_ = class_
    }
}

class TextElement(val text: String) : AbstractElement("TextElement") {
    override fun render(builder: Appendable) {
        builder.append(HtmlEscapers.htmlEscaper().escape(text))
    }
}

abstract class SingleElement(name: String, id: String?, class_: String?) : FindableElement(name = name, id = id, class_ = class_) {
    override fun render(builder: Appendable) {
        renderOpen(builder)
    }
}

abstract class Element(name: String, id: String?, class_: String?) : FindableElement(name = name, id = id, class_ = class_) {
    protected val children: MutableList<AbstractElement> = arrayListOf()

    protected fun addChild<T : AbstractElement>(e: T): T {
        children.add(e)
        return e
    }

    override fun render(builder: Appendable) {
        renderOpen(builder)
        for (child in children) {
            child.render(builder)
        }
        builder.append("</").append(elementName).append(">")
    }
}

abstract class ContainerElement(name: String, id: String?, class_: String?) : Element(name = name, id = id, class_ = class_) {
    fun h1(id: String? = null, class_: String? = null, text: String? = null): H1 {
        return addChild(H1(id = id, class_ = class_, text = text))
    }

    fun div(id: String? = null, class_: String? = null, text: String? = null, init: Div.() -> Unit = {}): Div {
        return addChild(Div(id = id, class_ = class_, text = text, init = init))
    }

    fun table(id: String? = null, class_: String? = null, init: Table.() -> Unit = {}): Table {
        return addChild(Table(id = id, class_ = class_, init = init))
    }
}

class Html(lang: String? = null, init: Html.() -> Unit = {}) : Element(name = "html", id = null, class_ = null) {
    var lang by attributeHandler

    {
        this.lang = lang
        init()
    }

    override fun render(builder: Appendable) {
        builder.append("<!DOCTYPE html>")
        super.render(builder)
    }

    fun head(init: Head.() -> Unit = {}): Head {
        val element = Head(init = init)
        children.add(element)
        return element;
    }

    fun body(id: String? = null, class_: String? = null, init: Body.() -> Unit = {}): Body {
        val body = Body(id = id, class_ = class_, init = init)
        children.add(body)
        return body
    }
}

class Head(init: Head.() -> Unit = {}) : Element(name = "head", id = null, class_ = null) {
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

    fun link(href: String? = null, rel: String? = null): Link {
        val link = Link(href = href, rel = rel)
        children.add(link)
        return link
    }
}

class Meta(charset: String? = null, httpEquiv: String? = null, name: String? = null, content: String? = null, init: Meta.() -> Unit = {}) : SingleElement(name = "meta", id = null, class_ = null) {
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

class Title(text: String? = null) : Element(name = "title", id = null, class_ = null) {
    {
        if (text != null) {
            children.add(TextElement(text))
        }
    }
}

class Link(href: String? = null, rel: String? = null) : SingleElement(name = "link", id = null, class_ = null) {
    var href by attributeHandler
    var rel by attributeHandler

    {
        this.href = href
        this.rel = rel
    }
}

class Body(id: String? = null, class_: String? = null, init: Body.() -> Unit = {}) : ContainerElement(name = "body", id = id, class_ = class_) {
    {
        init()
    }

    fun script(id: String? = null, class_: String? = null, src: String? = null): Script {
        val script = Script(id = id, class_ = class_, src = src)
        children.add(script)
        return script
    }
}

class Script(id: String? = null, class_: String? = null, src: String? = null) : Element(name = "script", id = id, class_ = class_) {
    var src by attributeHandler

    {
        this.src = src
    }
}

class H1(id: String? = null, class_: String? = null, text: String? = null) : Element(name = "h1", id = id, class_ = class_) {
    {
        if (text != null) {
            children.add(TextElement(text = text))
        }
    }
}

class Div(id: String? = null, class_: String? = null, text: String? = null, init: Div.() -> Unit = {}) : ContainerElement("div", id, class_) {
    {
        if (text != null) {
            children.add(TextElement(text = text))
        }
        init()
    }
}

class Table(id: String? = null, class_: String? = null, init: Table.() -> Unit = {}) : Element(name = "table", id = id, class_ = class_) {
    {
        init()
    }

    fun tr(id: String? = null, class_: String? = null, init: Tr.() -> Unit = {}): Tr {
        return addChild(Tr(id = id, class_ = class_, init = init))
    }
}

class Tr(id: String? = null, class_: String? = null, init: Tr.() -> Unit = {}) : Element(name = "tr", id = id, class_ = class_) {
    {
        init()
    }

    fun td(id: String? = null, class_: String? = null, text: String? = null, init: Td.() -> Unit = {}) : Td {
        return addChild(Td(id = id,  class_ = class_, text = text, init = init))
    }
}

class Td(id: String? = null, class_: String? = null, text: String? = null, init: Td.() -> Unit = {}) : ContainerElement(name = "td", id = id, class_ = class_) {
    {
        if (text != null) {
            children.add(TextElement(text = text))
        }
        init()
    }
}
