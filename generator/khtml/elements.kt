package com.khtml

public abstract class Element(elementName: String, {% for _, name in element_attrs %}{{name}}:String? = null{% if not loop.last %}, {% endif %}{%endfor%}): BaseElement(elementName) {
    {% for _, name in element_attrs -%}
    public var {{name}}: String? by attributeHandler
    {% endfor %}

    init {
        {% for _, name in element_attrs -%}
            this.{{name}} = {{name}}
        {% endfor %}
     }
}

{% for name, class_name, all_attrs, my_attrs in elements %}
public class {{class_name}}({% for _, i in all_attrs %}{{i}}: String? = null{% if not loop.last%}, {% endif %}{% endfor %}): Element(elementName = "{{name}}", {% for _, i in element_attrs %}{{i}} = {{i}}{% if not loop.last  %}, {% endif %}{% endfor %}) {
    {% for _, i in my_attrs -%}
        public var {{i}}: String? by attributeHandler
    {% endfor %}

    init {
        {% for _, i in my_attrs -%}
            this.{{i}} = {{i}}
        {% endfor %}
    }
}
{% endfor %}
