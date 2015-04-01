package com.khtml.elements

import com.khtml.BaseElement
import com.khtml.BaseEmptyElement
import com.khtml.attributeHandler

public abstract class EmptyElement(elementName: String, {% for _, name in element_attrs %}{{name}}:String? = null{% if not loop.last %}, {% endif %}{%endfor%}): BaseEmptyElement(elementName = elementName) {
    {% for _, name in element_attrs -%}
    public var {{name}}: String? by attributeHandler
    {% endfor %}

    init {
        {% for _, name in element_attrs -%}
            this.{{name}} = {{name}}
        {% endfor %}
     }
}

public abstract class Element(elementName: String, text_:String?, {% for _, name in element_attrs %}{{name}}:String? = null{% if not loop.last %}, {% endif %}{%endfor%}): BaseElement(elementName = elementName, text_ = text_) {
    {% for _, name in element_attrs -%}
    public var {{name}}: String? by attributeHandler
    {% endfor %}

    init {
        {% for _, name in element_attrs -%}
            this.{{name}} = {{name}}
        {% endfor %}
     }
}

{% for name, class_name, all_attrs, my_attrs, is_empty in elements %}
public class {{class_name}}({% if not is_empty %}text_:String? = null, {% endif %}{% for _, i in all_attrs %}{{i}}: String? = null{% if not loop.last%}, {% endif %}{% endfor %}): {{ is_empty and 'EmptyElement' or 'Element'}}(elementName = "{{name}}", {% if not is_empty %}text_ = text_, {% endif %}{% for _, i in element_attrs %}{{i}} = {{i}}{% if not loop.last  %}, {% endif %}{% endfor %}) {
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
