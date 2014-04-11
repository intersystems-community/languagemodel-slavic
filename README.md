languagemodel-slavic
====================
[![Build Status](https://api.travis-ci.org/intersystems-ru/languagemodel-slavic.png?branch=master)](https://travis-ci.org/intersystems-ru/languagemodel-slavic)

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

### Morphological analysis

If such information is provided by dictionaries, _hunspell_ can also perform morphological analysis – see [hunspell(4)](http://manpages.ubuntu.com/manpages/trusty/man4/hunspell.4.html) man page, section "Optional data fields".

### Java API status

2 projects are available – [one](https://github.com/dren-dk/HunspellJNA) is based on _JNA_ and [the other one](https://github.com/thomas-joiner/HunspellBridJ) on _BridJ_.

### Mac OS X status

Despite _Mac OS X_ inherently relies on _hunspell_ for spell checking tasks and supprts 3rd party _hunspell_ dictionaries, its _Objective C_ API doesn't support stemming nor morphological analysis (see [`NSSpellChecker` class reference](https://developer.apple.com/library/mac/documentation/cocoa/reference/applicationkit/classes/NSSpellChecker_Class/Reference/Reference.html)).

#### Mac OS X Java API

["Mac OS X for Java Geeks"](http://books.google.ru/books?id=Ip7ZgtqwC5cC&pg=PA177&lpg=PA177&dq=mac+os+x+spelling+framework&source=bl&ots=OyJ0A03_dj&sig=KzCRiBaqItVHhA-d7TGaqxTIvGY&hl=en&sa=X&ei=2KpDU9TFOKa34wTjuIG4BA&redir_esc=y#v=onepage&q=mac%20os%20x%20spelling%20framework&f=false) (Chapter 11, "The Mac OS X Spelling Framework"), refers to `com.apple.spell.ui` Java package, but the book has been published in 2003, and covers _Mac OS X_ 10.2 and _JDK_ 1.4. The package mentioned is missing from _Mac OS X_ 10.9 distribution. The _Apple_-shipped _Java_ packages are instead:

 * `apple.applescript`
 * `apple.awt`
 * `apple.keychain` (_JDK_ 1.4 only)
 * `apple.laf`
 * `apple.launcher`
 * `apple.security`
 * `apple.util`
 * `com.apple.concurrent`
 * `com.apple.crypto`
 * `com.apple.dnssd`
 * `com.apple.eawt`
 * `com.apple.eio`
 * `com.apple.java`
 * `com.apple.jobjc` (particularly, contains `com.apple.jobjc.appkit.NSSpellChecker` and `com.apple.jobjc.foundation.NSSpellServer` classes)
 * `com.apple.laf`
 * `com.apple.mrj`
 * `com.apple.resources`

## seman by [aot.ru](http://aot.ru/)

 * [Main site](http://aot.ru/)
 * [Source code (C++)](https://sourceforge.net/projects/seman/)
 * [Rudimental Java API](https://sourceforge.net/projects/seman-java/)

### Stemming and morphological analysis (Linux)

```
$ for w in 'друг' 'друзья' 'люди' 'какая'; do echo $w; done | iconv -t CP1251 | ./TestLem russian  | iconv -f CP1251
Loading..
Input a word..
+ ДРУГ С од мр,им,ед 147889 ДРУ'Г
+ ДРУГ С од мр,им,мн 147889 ДРУЗЬЯ'
+ ЧЕЛОВЕК С од мр,им,мн 135031 ЛЮ'ДИ
+ КАКАТЬ ДЕЕПРИЧАСТИЕ нп,нс дст,нст 151931 КА'КАЯ	+ КАКОЙ МС-П  но,од,жр,им,ед 148987 КАКА'Я
```

### Syntax analysis (Linux)

```
$ echo 'Варкалось, хливкие шорьки пырялись по наве' | iconv -t CP1251 | ./TestSynan russian | iconv -f CP1251
ok
sentences count: 1
sentences count: 1
<chunk>
<input>Варкалось, хливкие шорьки пырялись по наве</input>
<sent>
	<synvar>
		<clause type="ГЛ_ЛИЧН">Варкалось , хливкие шорьки пырялись по наве</clause>
		<group type="ПРИЛ_СУЩ">хливкие шорьки</group>
		<group type="ОДНОР_ИГ">Варкалось , хливкие шорьки</group>
		<group type="ПГ">по наве</group>
	</synvar>
	<rel name="ПРИЛ_СУЩ" gramrel="вн,им,мн," lemmprnt="ШОРЕК" grmprnt="но,мр,вн,им,мн," lemmchld="ХЛИВКИЙ" grmchld="но,од,вн,им,мн," > шорьки -> хливкие </rel>
	<rel name="ПГ" gramrel="пр," lemmprnt="ПО" grmprnt="" lemmchld="НАВ" grmchld="но,мр,пр,ед," > по -> наве </rel>
	<rel name="ОДНОР_ИГ" gramrel="вн,им,мн," lemmprnt="," grmprnt="" lemmchld="ВАРКАЛОСЬ" grmchld="но,ср,жр,мр,пр,тв,вн,дт,рд,им,ед,мн," > , -> Варкалось </rel>
	<rel name="ОДНОР_ИГ" gramrel="вн,им,мн," lemmprnt="," grmprnt="" lemmchld="ШОРЕК" grmchld="но,мр,вн,им,мн," > , -> шорьки </rel>
	<rel name="ПОДЛ" gramrel="" lemmprnt="ПЫРЯТЬСЯ" grmprnt="дст,нп,нс,прш,мн," lemmchld="ВАРКАЛОСЬ" grmchld="но,ср,жр,мр,пр,тв,вн,дт,рд,им,ед,мн," > пырялись -> Варкалось </rel>
</sent>
</chunk>
```

## mystem by [Yandex](https://company.yandex.ru/)

 * [Home page](https://company.yandex.ru/technologies/mystem/)
 * [Documentation](https://company.yandex.ru/technologies/mystem/help.xml)
 * [Ruby API](https://github.com/dmitry/yandex_mystem)

### Setting up

Version 2.1 for _Mac OS X_ is linked incorrectly against `/usr/local/Cellar/gcc47/4.7.2/gcc/lib/libstdc++.6.dylib`:

```
$ otool -L mystem
mystem:
	/usr/local/Cellar/gcc47/4.7.2/gcc/lib/libstdc++.6.dylib (compatibility version 7.0.0, current version 7.17.0)
	/usr/lib/libSystem.B.dylib (compatibility version 1.0.0, current version 169.3.0)
```

and dumps a core when run. The problem can be fixed with `install_name_tool`:

```
$ install_name_tool -change /usr/local/Cellar/gcc47/4.7.2/gcc/lib/libstdc++.6.dylib /usr/lib/libstdc++.6.dylib mystem
$ otool -L mystem
mystem:
	/usr/lib/libstdc++.6.dylib (compatibility version 7.0.0, current version 7.17.0)
	/usr/lib/libSystem.B.dylib (compatibility version 1.0.0, current version 169.3.0)
```

### Invocation example:

```
$ echo -e 'какая\nдрузья\nлюди\nваркалось\nхливкие\nшорьки\nглокая\nкуздра' | ./mystem -n -e utf-8 -i -l
какать=V,несов,нп=непрош,деепр|какой=APRO=им,ед,жен
друг=S,муж,од=им,мн
человек=S,муж,од=им,мн
варкаться?=V,несов,нп=прош,ед,изъяв,сред
хливкий?=A=им,мн,полн|?=A=вин,мн,полн,неод
шорька?=S,жен,неод=им,мн|?=S,жен,неод=род,ед|?=S,жен,неод=вин,мн
глокать?=V,несов,нп=непрош,деепр|глокий?=A=им,ед,полн,жен
куздра?=S,ед,жен,неод=им|куздра?=S,гео,жен,неод=им,ед
```

## Apache Lucene

[Apache Licene](https://lucene.apache.org/core/) contains a port of C++ _hunspell_ API to Java, see the [API documentation](https://lucene.apache.org/core/3_6_0/api/all/index.html?org/apache/lucene/analysis/hunspell/package-summary.html).

## LanguageTool

 * [Java Spell Checker](http://wiki.languagetool.org/java-spell-checker)
 * [Java API](http://wiki.languagetool.org/java-api)
 * [API documentation](https://languagetool.org/development/api/)

# Feature comparison

## Human languages support

Colons can be used to align columns.

| Product       | Russian | Ukrainian | English | German  | Morphological Analysis                 | Syntax Analysis |
| ------------- |:-------:|:---------:|:-------:|:-------:| -------------------------------------- | --------------- |
| hunspell      | **yes** | **yes**   | **yes** | **yes** | **yes** (if supported by dictionaries) | no              |
| seman         | **yes** | no        | **yes** | **yes** | **yes**                                | **yes**         |
| mystem        | **yes** | no        | no      | no      | **yes**                                | no              |
| LanguageTool  | **yes** | **yes**   | **yes** | **yes** | **yes**                                | no              |
| Lucene        | ?       | ?         | ?       | ?       | ?                                      | no              |

## Programming languages support

| Product       | C++     | Java      |
| ------------- |:-------:|:---------:|
| hunspell      | **yes** | **yes**   |
| seman         | **yes** | no        |
| mystem        | **yes** | no        |
| LanguageTool  | no      | **yes**   |
| Lucene        | no      | **yes**   |

## OS support

| Product       | Windows | Linux   | Mac OS X |
| ------------- |:-------:|:-------:|:--------:|
| hunspell      | **yes** | **yes** | **yes**
| seman         | **yes** | **yes** | no
| mystem        | **yes** | **yes** | **yes**
| LanguageTool  | **yes** | **yes** | **yes**
| Lucene        | **yes** | **yes** | **yes**

## License

| Product       | License        | Can be distributed with Caché? |
| ------------- |----------------|:------------------------------:|
| hunspell      | GPL/LGPL/MPL   | yes
| seman         | LGPL           | yes
| mystem        | [non-commercial](https://legal.yandex.ru/mystem/) | no
| LanguageTool  | LGPL           | yes
| Lucene        | Apache License | yes

# Native (C++) implementation

For C++ implementation, it is possible to link against either _hunspell_, _seman_ or _mystem_ and return the results of morphological analytis as a _JSON_ object using [Boost Property Tree](http://www.boost.org/doc/libs/1_41_0/doc/html/boost_propertytree/parsers.html#boost_propertytree.parsers.json_parser)

[ZWARRAYP](http://docs.intersystems.com/ens20131/csp/docbook/DocBook.UI.Page.cls?KEY=RCOS_fzf61) type can be used to pass strings from/to Caché.
