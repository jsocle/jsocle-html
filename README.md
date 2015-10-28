JSOCLE-HTML - TypeSafe HTML Builder
===================================

뭐에 쓰는 물건인고?
--------------

HTML 을 안전하고 편리하고 완벽하게 kotlin 으로 표현할수 있도록 해준다.

Why?
----

* Kotlin Everywhere
* Seamless - 모든것을 프로그래밍 할 수 있다는 것 자체가 장점이다.

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

Safe
----
자동으로 모든 문자열을 escape 처리함
```kotlin
val danger = """<script src="http://crack.com/crack.js"></script>"""
val p = P(text_ = danger)
println(p)
```
```html
<p>&lt;script&gt;http://crack.com/crack.js&lt;/script&gt;</p>
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
