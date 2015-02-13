package ko.html

abstract class AbstractElement(val name: String) {
    abstract fun render(builder: StringBuilder)

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder)
        return builder.toString()
    }
}

abstract class Element(name: String) : AbstractElement(name) {
    override fun render(builder: StringBuilder) {
        builder.append("<$name></$name>")
    }
}

open class Html : Element("html") {
    override fun render(builder: StringBuilder) {
        builder.append("<!DOCTYPE html>")
        super.render(builder)
    }
}

class Body : Element("body") {
}
