# xIS-Search
## How to add Java searcher
Add `MySearcher.java` under `src/main/java/services/xis/search/searcher`:
```java
package services.xis.search.searcher;

import org.elasticsearch.index.query.QueryBuilder;

public class MySearcher extends GenBuilderSearcher {
    public MySearcher(
        String host, Integer port0, Integer port1, String protocol
    ) {
        super(host, port0, port1, protocol);
    }

    public QueryBuilder builder(String key) {
        // implementation
    }
}
```
For more customization, extends `Searcher` directly:
```java
package services.xis.search.searcher;

import java.io.IOException;
import org.elasticsearch.action.search.SearchResponse;

public class MySearcher extends Searcher {
    public MySearcher(
        String host, Integer port0, Integer port1, String protocol
    ) {
        super(host, port0, port1, protocol);
    }

    public SearchResponse search(String index, String typ, String key)
        throws IOException {
        // implementation
    }
}
```
## How to add Scala searcher
Add `MySearcher.scala` under `src/main/scala/services/xis/search/searcher`:
```scala
package services.xis.search.searcher

import org.elasticsearch.index.query.QueryBuilder

class MySearcher(
  host: String,
  port0: java.lang.Integer,
  port1: java.lang.Integer,
  protocol: String
) extends GenBuilderSearcher(
  host, port0, port1, protocol
) {
  def builder(key: String): QueryBuilder = {
    // implementation
  }
}
```
For more customization, extends `Searcher` directly:
```scala
package services.xis.search.searcher

import org.elasticsearch.action.search.SearchResponse

class MySearcher(
  host: String,
  port0: java.lang.Integer,
  port1: java.lang.Integer,
  protocol: String
) extends Searcher(
  host, port0, port1, protocol
) {
  def search(index: String, typ: String, key: String): SearchResponse = {
    // implementation
  }
}
```
## How to run
* Provice a class name and a search keyword as command line arguments.
* If a file name is given, then the `stdout` will be redirected to the file.
```shell
$ sbt
sbt:xis-search> run [index] [class] [keyword]
sbt:xis-search> run [index] [class] [keyword] [file]
sbt:xis-search> run portal MySearcher "수강신청"
sbt:xis-search> run portal MySearcher "수강신청" log
sbt:xis-search> run --score [label] [index] [class] [keyword]
sbt:xis-search> run --score data.csv portal MySearcher "수강신청"
```
* You can customize output by making the `format` file.
* If the `format` file does not exist, then the default format is applied:
```
\N(\S). portal.kaist.ac.kr/ennotice/\B/\I
[\T](\t)
Content: \c80
Image: \i80
Attach: \a80
```
### Formatting guideline
* `\\` -> `\`
* `\N` -> `[rank]`
* `\S` -> `[score]`
* `\E` -> `[explanation]`
* `\I` -> `[id]`
* `\B` -> `[board]`
* `\T` -> `[title]`
* `\A` -> `[author]`
* `\d` -> `[department]`
* `\t` -> `[time]`
* `\h` -> `[hits]`
* `\c` -> `[content]`
* `\a` -> `[attached]`
* `\i` -> `[image]`
* `\[char][int]` -> `[value].substring(0, [int])`
