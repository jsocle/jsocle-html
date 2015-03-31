import wget

for tag in map(lambda x: x.strip(), open('tags')):
    tag = tag[1:-1]
    if tag[0] == '!':
        continue
    name = tag
    url_name = ''
    if name[0] == 'h':
        if name == 'h1':
            url_name = 'hn'
    else:
        url_name = name
    if url_name:
        url = 'http://www.w3schools.com/tags/tag_%s.asp' % url_name
        wget.download(url, out='w3schools/' + url_name + '.html')

wget.download('http://www.w3schools.com/tags/ref_standardattributes.asp', out='w3schools/global_attributes.html')
wget.download('http://www.w3schools.com/tags/ref_eventattributes.asp', out='w3schools/global_events.html')
