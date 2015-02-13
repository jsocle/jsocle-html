package ko.html

abstract class Tag {
    abstract fun render(builder: StringBuilder)

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder)
        return builder.toString()
    }
}

open class Html : Tag() {
    override fun render(builder: StringBuilder) {
        builder.append("<html></html>")
    }
}


class Html5 : Html() {
    override fun render(builder: StringBuilder) {
        builder.append("<!DOCTYPE html>")
        super.render(builder)
    }
}


class Body : Tag() {
    override fun render(builder: StringBuilder) {
        builder.append("<body></body>")
    }
}
