package ko.html

abstract class Tag {
    abstract fun render(builder: StringBuilder)

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder)
        return builder.toString()
    }
}

class Html : Tag() {
    override fun render(builder: StringBuilder) {
        builder.append("<html></html>")
    }
}

fun html(init: (Html.() -> Unit) = {}): Html {
    val html = Html()
    html.init()
    return html
}
