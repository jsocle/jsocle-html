# coding=utf-8
from __future__ import unicode_literals, print_function, absolute_import, \
    division
from pyquery import PyQuery


def norm(prop):
    if prop == 'class':
        return 'class_'
    if prop == 'data-*':
        return 'data'
    return prop


def parse(name):
    html = PyQuery(open('w3schools/' + name + '.html').read())
    res = []
    for table in map(PyQuery, html('table')):
        if table('tr:eq(0) th:eq(0)').text() == 'Attribute':
            res += map(lambda x: PyQuery(x).text(), table('tr:gt(1) td:eq(0)'))
    return map(lambda x: (x, norm(x)), res)


def props(name):
    return global_attributes + parse(name) + global_events


global_attributes = parse('global_attributes')
global_events = parse('global_events')

if __name__ == '__main__':
    print(global_attributes)
    print(global_events)
    print(parse('a'))
    print(props('a'))
