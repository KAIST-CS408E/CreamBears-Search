# xIS-Search
## How to add Java searcher
Add `MySearcher.java` under `src/main/java/services/xis/search/searcher`:
```java
package services.xis.search.searcher;

public class NaiveContentSearcher extends Searcher {
    private String index, typ;

    public NaiveContentSearcher(
        String host, Integer port0, Integer port1,
        String protocol, String index, String typ
    ) {
        super(host, port0, port1, protocol);
        this.index = index;
        this.typ = typ;
    }

    public void search(String key) {
        // implementation
    }
}
```
## How to add Scala searcher
Add `MySearcher.scala` under `src/main/scala/services/xis/search/searcher`:
```scala
package services.xis.search.searcher

class NaiveTitleSearcher(
  host: String,
  port0: java.lang.Integer,
  port1: java.lang.Integer,
  protocol: String,
  index: String,
  typ: String
) extends Searcher(
  host, port0, port1, protocol
) {
  def search(key: String): Unit = {
    // implementation
  }
}
```
## How to run
```shell
$ sbt
sbt:xis-search> run [class] [keyword]
sbt:xis-search> run MySearcher "수강 신청"
```
