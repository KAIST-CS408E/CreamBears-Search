package services.xis.search.searcher

import java.lang.Integer

import org.elasticsearch.index.query.{
  QueryBuilder, QueryBuilders, MatchQueryBuilder
}

abstract class BaseSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends GenBuilderSearcher(host, port0, port1, protocol) {
  def builder(key: String): MatchQueryBuilder
}

trait BaseMixin extends BaseSearcher {
  val field: String
  def builder(key: String): MatchQueryBuilder =
    QueryBuilders.matchQuery(field, key)
}

class BaseTitleSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends BaseSearcher(host, port0, port1, protocol) with BaseMixin {
  val field: String = "title"
}

class BaseContentSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends BaseSearcher(host, port0, port1, protocol) with BaseMixin {
  val field: String = "content"
}

class BaseImageSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends BaseSearcher(host, port0, port1, protocol) with BaseMixin {
  val field: String = "image"
}

class BaseAttachedSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends BaseSearcher(host, port0, port1, protocol) with BaseMixin {
  val field: String = "attached"
}
