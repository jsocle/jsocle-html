import names
tag_names = filter(lambda x: not x.startswith('__'), dir(names))

if __name__ == '__main__':
    print(dir(tag_names))