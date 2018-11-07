package services.xis.search.searcher

import org.elasticsearch.index.query.{
  QueryBuilder, QueryBuilders, BoolQueryBuilder
}

class TextSearcher(
  host: String,
  port0: java.lang.Integer,
  port1: java.lang.Integer,
  protocol: String
) extends GenBuilderSearcher(
  host, port0, port1, protocol
) {
  def builder(key: String): QueryBuilder =
    new BoolQueryBuilder()
      .should(QueryBuilders.matchQuery("title", key).boost(2f))
      .should(QueryBuilders.matchQuery("content", key))
      .should(QueryBuilders.matchQuery("image", key).boost(.5f))
      .should(QueryBuilders.matchQuery("attached", key).boost(.2f))
}
