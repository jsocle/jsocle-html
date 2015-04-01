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


public abstract class Element0(elementName: String, text_:String? = null, {% for _, name in element_attrs %}{{name}}:String? = null{% if not loop.last %}, {% endif %}{%endfor%}): BaseElement(elementName = elementName, text_ = text_) {
    {% for _, name in element_attrs -%}
    public var {{name}}: String? by attributeHandler
    {% endfor %}

    init {
        {% for _, name in element_attrs -%}
            this.{{name}} = {{name}}
        {% endfor %}
     }
}

{% for case, case_succ in cases %}
public abstract class Element{{case.upper()}}(elementName: String, text_:String?, {% for _, name in element_attrs %}{{name}}:String? = null{% if not loop.last %}, {% endif %}{%endfor%}):Element{{ case_succ.upper() }}(elementName = elementName, text_ = text_, {% for _, name in element_attrs %}{{name}} = {{name}}{% if not loop.last %}, {% endif %}{%endfor%}) {
    {% for name, class_name, all_attrs, my_attrs, is_empty, fun_name in elements if name[0] == case %}
        public fun {{fun_name}}({% if not is_empty %}text_:String? = null, {% endif %}{% for _, i in all_attrs %}{{i}}: String? = null{% if not loop.last%}, {% endif %}{% endfor %}, init: {{class_name}}.() -> Unit = {}): {{class_name}} {
            val el = {{class_name}}({% if not is_empty %}text_ = text_, {% endif %}{% for _, i in all_attrs %}{{i}} = {{i}}{% if not loop.last%}, {% endif %}{% endfor %}, init = init)
            addNode(el)
            return el
        }
    {% endfor %}
}
{% endfor %}

public abstract class Element(elementName: String, text_:String? = null, {% for _, name in element_attrs %}{{name}}:String? = null{% if not loop.last %}, {% endif %}{%endfor%}): ElementZ(elementName = elementName, text_ = text_, {% for _, name in element_attrs %}{{name}} = {{name}}{% if not loop.last %}, {% endif %}{%endfor%})
