package services.xis.search.searcher

import org.elasticsearch.index.query.{QueryBuilder, QueryBuilders}

class NaiveTitleSearcher(
  host: String,
  port0: java.lang.Integer,
  port1: java.lang.Integer,
  protocol: String
) extends GenBuilderSearcher(
  host, port0, port1, protocol
) {
  def builder(key: String): QueryBuilder =
    QueryBuilders.matchQuery("title", key)
}
