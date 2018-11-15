package services.xis.search.searcher

import java.lang.Integer

import org.elasticsearch.index.query.{
  QueryBuilder, QueryBuilders, MatchQueryBuilder
}

abstract class BaseSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String, field: String
) extends GenBuilderSearcher(host, port0, port1, protocol) {
  def builder(key: String): MatchQueryBuilder =
    QueryBuilders.matchQuery(field, key)
}

class BaseTitleSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends BaseSearcher(host, port0, port1, protocol, "title")

class BaseContentSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends BaseSearcher(host, port0, port1, protocol, "content")

class BaseImageSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends BaseSearcher(host, port0, port1, protocol, "image")

class BaseAttachedSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends BaseSearcher(host, port0, port1, protocol, "attached")
