# Java Examples

Simple experimental Java program examples

- [BirIslem](./BirIslem): An educational implementation of Bir İşlem (Le compte est bon - the total is right) game using
  genetic algorithms (comments are in Turkish)
- [Chat](./Chat): A chat implementation (2007)
- [CurrencyReader](./CurrencyReader): A XML reading and parsing example (2006)
- [DB2POJO](./DB2POJO): An example project for creating POJO classes from database (2006)
- [DSRMI](./DSRMI): An example project for
  using [Java RMI](https://docs.oracle.com/en/java/javase/11/docs/api/java.rmi/java/rmi/package-summary.html)
  and [Jackson XML library](https://github.com/FasterXML/jackson-dataformat-xml) (2006)
- [Java 3D Examples](./Java3DExamples): Simple examples of how to use
  [Java 3D API](http://www.oracle.com/technetwork/articles/javase/index-jsp-138252.html)
  to create scenes.
- [JDBC](./JDBC): An example project on how to use [JDBC](https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html)
- [JDIP](./JDIP): A simple Digital Image Processing application that shows various
  [Image Processing filters](http://www.jhlabs.com/ip/filters/index.html) on images (2006)
- [Notepad](./Notepad): A very simple notepad implementation that is able to load TXT files
- [OnlineTranslator](./OnlineTranslator): A simple online translator to show how to
  use [Apache HttpComponents](https://hc.apache.org/) and
  [jsoup](https://jsoup.org/) libraries for requesting web pages and parsing them.
- [Reflection](./Reflection):
  [Java Reflection API](https://docs.oracle.com/javase/tutorial/reflect/) usage example with in Java (2005)
- [SimpleBrowser](./SimpleBrowser): An example browser implementation that uses sqlite3 to store sites (2009)
- [SocketCalculator](./SocketCalculator): A calculator over socket connection in Java (2006)
- [ThreadSync](./ThreadSync): A thread synchronization example (2006)
- [xox](./xox): A Tic Tac Toe implementation based on minimax (2006)

All projects are using [maven](https://maven.apache.org).

It is possible to build executable JARs using the following command in project level:

    mvn clean install

And run generated JAR file in `target` folder of `CurrencyReader`, `BirIslem`, `Chat`, `DB2POJO`, `JDIP`
, `Java3DExamples`
, `Notepad`, `OnlineTranslator`, `Reflection`, `SimpleBrowser`, `ThreadSync`, and `xox` projects (where `main.class`
property is available in their `pom.xml` file.):

    java -jar <project name>/target/<name of the JAR file>

## LICENSE

All these programs are a free software: you can redistribute them and/or modify them under the terms of the
[GNU General Public License](https://www.gnu.org/licenses/gpl-3.0.en.html) as published by
the [Free Software Foundation](https://www.fsf.org), either version 3 of the License, or (at your option) any later
version.

This programs are distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the [GNU General Public License](./LICENSE) for
more details.
