languagemodel-slavic
====================
Please follow [this link](http://intersystems-ru.github.io/languagemodel-slavic/) for project documentation.

# Stemming engines available

## Hunspell

Dictionary locations:

 * `/usr/share/hunspell`
 * `/usr/local/share/hunspell`

On _Mac OS X_, which also relies on _hunspell_ for spell checking purposes, additional dictionary locations can be examined:

 * `/System/Library/Spelling`
 * `/Library/Spelling`
 * `~/Spelling`
 * `/opt/local/share/hunspell` (in case [MacPorts](https://www.macports.org/) are installed)
 * `/sw/share/hunspell` (in case [Fink](http://finkproject.org/) is installed)

## seman by [aot.ru](http://aot.ru/)

 * [Main site](http://aot.ru/)
 * [Source code (C++)](https://sourceforge.net/projects/seman/)
 * [Rudimental Java API](https://sourceforge.net/projects/seman-java/)

## mystem by [Yandex](https://company.yandex.ru/)

Invocation example:

    $ echo -e 'какая\nдрузья\nлюди\nваркалось\nхливкие\nшорьки\nглокая\nкуздра' | ./mystem -n -e utf-8 -i -l
    какать=V,несов,нп=непрош,деепр|какой=APRO=им,ед,жен
    друг=S,муж,од=им,мн
    человек=S,муж,од=им,мн
    варкаться?=V,несов,нп=прош,ед,изъяв,сред
    хливкий?=A=им,мн,полн|?=A=вин,мн,полн,неод
    шорька?=S,жен,неод=им,мн|?=S,жен,неод=род,ед|?=S,жен,неод=вин,мн
    глокать?=V,несов,нп=непрош,деепр|глокий?=A=им,ед,полн,жен
    куздра?=S,ед,жен,неод=им|куздра?=S,гео,жен,неод=им,ед


# Feature comparison

## Human languages support

Colons can be used to align columns.

| Product       | Russian | Ukrainian | English | German | Morphological Analysis             |
| ------------- |:-------:|:---------:|:-------:|:------:|------------------------------------|
| hunspell      | yes     | yes       | yes     | yes    | yes[^hunspell-dicts] |
| seman         | yes     | no        | yes     | yes    | yes                                |
| mystem        | yes     | no        | no      | no     | yes                                |


[^hunspell-dicts]: if supported by dictionaries


## Programming languages support

## OS support

## License

# Native (C++) implementation

For C++ implementation, it is possible to link against either _hunspell_, _seman_ or _mystem_ and return the results of morphological analytis as a _JSON_ object using [Boost Property Tree](http://www.boost.org/doc/libs/1_41_0/doc/html/boost_propertytree/parsers.html#boost_propertytree.parsers.json_parser)
