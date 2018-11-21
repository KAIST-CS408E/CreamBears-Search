package services.xis.search.searcher

import java.lang.Integer

import org.elasticsearch.index.query.{
  QueryBuilder, QueryBuilders, MatchQueryBuilder,
  BoolQueryBuilder, ConstantScoreQueryBuilder
}

abstract class ConstBaseSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String, field: String
) extends GenBuilderSearcher(host, port0, port1, protocol) {
  def builder(key: String): ConstantScoreQueryBuilder =
    new ConstantScoreQueryBuilder(
      new BoolQueryBuilder().filter(
        QueryBuilders.matchQuery(field, key)
      )
    )
}

class ConstBaseTitleSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends ConstBaseSearcher(host, port0, port1, protocol, "title")

class ConstBaseContentSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends ConstBaseSearcher(host, port0, port1, protocol, "content")

class ConstBaseImageSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends ConstBaseSearcher(host, port0, port1, protocol, "image")

class ConstBaseAttachedSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends ConstBaseSearcher(host, port0, port1, protocol, "attached")
