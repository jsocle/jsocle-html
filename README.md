JSOCLE-HTML - TypeSafe HTML Builder
===================================

뭐에 쓰는 물건인고?
--------------

HTML 을 안전하고 편리하게 표현할수 있도록 해준다.

Hello, world
------------
```kotlin
val html = Html {
    body {
        h1(text_ = "hello, world")
    }
}

println(html.render())
```

```html
<html>
    <body>
        <h1>hello, world</h1>
    </body>
</html>
```

class helpers
-------------
```kotlin
val div = Div(class_ = "box")
div.addClass("box-errors") // <div class="box box-errors"></div>

if ("box" in div.classes) { // access current classes
    div.addClass("box-wide")
}
```

data attribute
--------------
```kotlin
val div = Div()
div.data_["userId"] = 1
div.data_["userName]" = "John"

println(div)
```

```html
<div data-user-id="1" data-user-name="John"></div>
```

None Declarative Usage
----------------------
html 후처리 가능
```kotlin
fun evenly(list: List<Li>) {
    list.forEachIndexed { i, li -> if (i % 2 == 0) li.addClass("even") }
}

val numbers = listOf(1, 2, 3, 4, 5)
val ul = Ul {
    val liList = numbers.map {
        li(text_ = it.toString())
    }
    evenly(liList)
}
println(ul)
```
```html
<ul>
    <li class="even">1</li>
    <li>2</li>
    <li class="even">3</li>
    <li>4</li>
</ul>
```
