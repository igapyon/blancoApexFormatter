# blancoApexFormatter

blancoApexFormatter is a Apax language source code formatter.
blancoApexFormatter is written in Java. blancoApexFormatter is provided as an OSS product.

## spec

blancoApexFormatter format Apax code like below:

### before (input)

```java
@isTest
public without sharing class MySimpleTest {
         static testMethod void testMain001(){
              System.assert(false,
              'First hello word written in Apex, as a error of test code.');
}
}
```

### after (formatted output)

```java
@isTest
public without sharing class MySimpleTest {
    static testMethod void testMain001() {
        System.assert(false,
            'First hello word written in Apex, as a error of test code.');
    }
}
```

## usage

There is several way to use blancoApexFormatter.
My currently recommendation is to use blancoApexFormatterCli. blancoApexFormatterCli provides Ant Task and command line interface. See below:

### Ant task

Prepare Ant taskdef on your build.xml like below:

```xml
	<taskdef name="apexformatter" classname="blanco.apex.formatter.ant.BlancoApexFormatterTask">
		<classpath>
			<pathelement location="./lib/blancoApexFormatterCli.jar" />
			<pathelement location="./lib/blancoApexFormatter.jar" />
			<pathelement location="./lib/blancoApexSyntaxParser.jar" />
			<pathelement location="./lib/blancoApexParser.jar" />
			<pathelement location="./lib/apache/commons-cli-1.3.1.jar" />
			<pathelement location="./lib/apache/commons-io-2.5.jar" />
		</classpath>
	</taskdef>
```

Run Ant task of blancoApexFormatter like below:

```xml
	<target name="doFormat">
		<apexformatter input="./test/data/apex/"
		               output="./test/data/apex.output"/>
	</target>
```

'input' and 'output' attributes are required.

### Command line

#### how to run in command line

```
java -cp blancoApexFormatterCli.jar:lib/blancoApexFormatter.jar:lib/blancoApexSyntaxParser.jar:lib/blancoApexParser.jar:lib/apache/commons-cli-1.3.1.jar:lib/apache/commons-io-2.5.jar blanco.apex.formatter.cli.BlancoApexFormatterCli -i /home/tosiki/workspace/apex-lang/src.apex -o /home/tosiki/workspace/apex-lang/src.apex.output
```

Command line interface of blancoApexFormatter provides several option like below:

```
usage: BlancoApexFormatterCli
 -h,--help                   show usage.
 -i,--input <inputdir>       input directory.
 -o,--output <outputdir>     output directory.
 -v,--verbose                run verbose mode.
 -xbracket <true>            format bracket.
 -xcomma <true>              format comma.
 -xindent <true>             format indent.
 -xsemicolon <true>          format semicolon.
 -xsmashwhitespace <false>   format with whitespace smash (hard format).
 -xspecialchar <true>        format special char.
```

## download

You can download latest version of blancoApexFormatter from URL below:

  https://github.com/igapyon/blancoApexFormatterCli/releases

'Source code (zip)' contains binary jar files of blancoApexFormatter.

## LICENSE

```
/*
 * Copyright 2016 Toshiki Iga
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
```
