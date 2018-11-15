package services.xis.search.searcher

import org.elasticsearch.index.query.{
  QueryBuilder, QueryBuilders, BoolQueryBuilder
}

class SynonymSearcher(
  host: String,
  port0: java.lang.Integer,
  port1: java.lang.Integer,
  protocol: String
) extends GenBuilderSearcher(
  host, port0, port1, protocol
) {
  def builder(key: String): QueryBuilder =
    new BoolQueryBuilder()
      .should(QueryBuilders.matchQuery("title", key)
        .analyzer("portal_synonym").boost(2f))
      .should(QueryBuilders.matchQuery("content", key)
        .analyzer("portal_synonym"))
      .should(QueryBuilders.matchQuery("image", key)
        .analyzer("portal_synonym").boost(.5f))
      .should(QueryBuilders.matchQuery("attached", key)
        .analyzer("portal_synonym").boost(.2f))
}
