tags = [
    'a',
    'abbr',
    'acronym',
    'address',
    'applet',
    'area',
    'article',
    'aside',
    'audio',
    'b',
    'base',
    'basefont',
    'bdi',
    'bdo',
    'big',
    'blockquote',
    'body',
    'br',
    'button',
    'canvas',
    'caption',
    'center',
    'cite',
    'code',
    'col',
    'colgroup',
    'datalist',
    'dd',
    'del',
    'details',
    'dfn',
    'dialog',
    'dir',
    'div',
    'dl',
    'dt',
    'em',
    'embed',
    'fieldset',
    'figcaption',
    'figure',
    'font',
    'footer',
    'form',
    'frame',
    'frameset',
    'h1',
    'h2',
    'h3',
    'h4',
    'h5',
    'h6',
    'head',
    'header',
    'hr',
    'html',
    'i',
    'iframe',
    'img',
    'input',
    'ins',
    'kbd',
    'keygen',
    'label',
    'legend',
    'li',
    'link',
    'main',
    'map',
    'mark',
    'menu',
    'menuitem',
    'meta',
    'meter',
    'nav',
    'noframes',
    'noscript',
    'object',
    'ol',
    'optgroup',
    'option',
    'output',
    'p',
    'param',
    'pre',
    'progress',
    'q',
    'rp',
    'rt',
    'ruby',
    's',
    'samp',
    'script',
    'section',
    'select',
    'small',
    'source',
    'span',
    'strike',
    'strong',
    'style',
    'sub',
    'summary',
    'sup',
    'table',
    'tbody',
    'td',
    'textarea',
    'tfoot',
    'th',
    'thead',
    'time',
    'title',
    'tr',
    'track',
    'tt',
    'u',
    'ul',
    'var',
    'video',
    'wbr',
]

empty_tags = ['br', 'hr', 'meta', 'link', 'base', 'link', 'meta', 'hr', 'br', 'img', 'embed', 'param', 'area', 'col',
              'input']

keywords = ['object', 'var']


def func_name(name):
    if name in keywords:
        name += '_'
    return name


import string

from attributes import parse, global_attributes, global_events


if __name__ == '__main__':
    from jinja2 import Template

    element_attrs = sorted(set(global_attributes + global_events))
    elements = []
    for i in tags:
        my_attrs = parse(i)
        all_attrs = sorted(set(element_attrs + my_attrs))
        row = (
            i, i.capitalize(), all_attrs,
            sorted(j for j in my_attrs if j not in element_attrs),
            i in empty_tags, func_name(i)
        )
        elements.append(row)
    cases = [(c, i == 0 and '0' or string.ascii_lowercase[i - 1]) for i, c in enumerate(string.ascii_lowercase)]
    element_kt = Template(open('khtml/element.kt').read()).render(
        element_attrs=element_attrs, elements=elements, cases=cases
    )
    elements_kt = Template(open('khtml/elements.kt').read()).render(element_attrs=element_attrs, elements=elements)
    open('../src/main/kotlin/com/khtml/element.kt', 'w').write(element_kt)
    open('../src/main/kotlin/com/khtml/elements.kt', 'w').write(elements_kt)
