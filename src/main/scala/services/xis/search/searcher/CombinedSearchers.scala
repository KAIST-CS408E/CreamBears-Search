package services.xis.search.searcher

import java.lang.Integer

import org.elasticsearch.index.query.{
  QueryBuilder, QueryBuilders, BoolQueryBuilder
}

abstract class CombinedSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String,
  components: List[(GenBuilderSearcher, Float)]
) extends GenBuilderSearcher(host, port0, port1, protocol) {
  def builder(key: String): QueryBuilder =
    (new BoolQueryBuilder() /: components){
      case (builder, (searcher, boost)) =>
        builder.should(searcher.builder(key).boost(boost))
    }

  override def close(): Unit = {
    super.close()
    components.foreach(_._1.close())
  }
}

class TitleContentSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String,
) extends CombinedSearcher(
  host, port0, port1, protocol,
  List((new BaseTitleSearcher(host, port0, port1, protocol), 2f),
       (new BaseContentSearcher(host, port0, port1, protocol), 1f))
)

class AllFieldsSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String,
) extends CombinedSearcher(
  host, port0, port1, protocol,
  List((new BaseTitleSearcher(host, port0, port1, protocol), 2f),
       (new BaseContentSearcher(host, port0, port1, protocol), 1f),
       (new BaseImageSearcher(host, port0, port1, protocol), .5f),
       (new BaseAttachedSearcher(host, port0, port1, protocol), .2f))
)

class SynonymTitleContentSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String,
) extends CombinedSearcher(
  host, port0, port1, protocol,
  List((new SynonymTitleSearcher(host, port0, port1, protocol), 2f),
       (new SynonymContentSearcher(host, port0, port1, protocol), 1f))
)

class SynonymAllFieldsSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String,
) extends CombinedSearcher(
  host, port0, port1, protocol,
  List((new SynonymTitleSearcher(host, port0, port1, protocol), 2f),
       (new SynonymContentSearcher(host, port0, port1, protocol), 1f),
       (new SynonymImageSearcher(host, port0, port1, protocol), .5f),
       (new SynonymAttachedSearcher(host, port0, port1, protocol), .2f))
)

class AllFieldsConstSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String,
) extends CombinedSearcher(
  host, port0, port1, protocol,
  List((new ConstBaseTitleSearcher(host, port0, port1, protocol), 10f),
       (new ConstBaseContentSearcher(host, port0, port1, protocol), 5f),
       (new ConstBaseImageSearcher(host, port0, port1, protocol), 2.5f),
       (new ConstBaseAttachedSearcher(host, port0, port1, protocol), 1f))
)
