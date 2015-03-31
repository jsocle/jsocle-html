# coding=utf-8
from __future__ import unicode_literals, print_function, absolute_import, \
    division
from pyquery import PyQuery
import re

keywords = {'class', 'object'}


def norm(prop):
    if prop == 'data-*':
        prop = 'data'
    if prop in keywords:
        prop += '_'
    return re.sub('-([a-z])', lambda x: x.group(1).upper(), prop)


def parse(name):
    if name in ['h%d' % i for i in range(10)]:
        name = 'hn'
    html = PyQuery(open('w3schools/' + name + '.html').read())
    res = []
    for table in map(PyQuery, html('table')):
        if table('tr:eq(0) th:eq(0)').text() == 'Attribute':
            res += map(lambda x: PyQuery(x).text(), table('tr:gt(1) td:eq(0)'))
    return filter(lambda x: x[0] != 'data' and ':' not in x[0], map(lambda x: (x, norm(x)), res))


global_attributes = parse('global_attributes')
global_events = parse('global_events')

if __name__ == '__main__':
    print(global_attributes)
    print(global_events)
    print(parse('a'))
