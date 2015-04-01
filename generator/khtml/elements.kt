package com.khtml.elements

import com.khtml.attributeHandler

{% for name, class_name, all_attrs, my_attrs, is_empty, fun_name in elements %}
public class {{class_name}}({% if not is_empty %}text_:String? = null, {% endif %}{% for _, i in all_attrs %}{{i}}: String? = null{% if not loop.last%}, {% endif %}{% endfor %}, init: {{class_name}}.() -> Unit = {}): {{ is_empty and 'EmptyElement' or 'Element'}}(elementName = "{{name}}", {% if not is_empty %}text_ = text_, {% endif %}{% for _, i in element_attrs %}{{i}} = {{i}}{% if not loop.last  %}, {% endif %}{% endfor %}) {
    {% for _, i in my_attrs -%}
        public var {{i}}: String? by attributeHandler
    {% endfor %}

    init {
        {% for _, i in my_attrs -%}
            this.{{i}} = {{i}}
        {% endfor %}
        init()
    }
}
{% endfor %}
