package services.xis.search.searcher

import java.lang.Integer

import org.elasticsearch.index.query.{
  QueryBuilder, QueryBuilders, MatchQueryBuilder,
  BoolQueryBuilder, ConstantScoreQueryBuilder
}

abstract class SynonymConstBaseSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String, field: String
) extends GenBuilderSearcher(host, port0, port1, protocol) {
  def builder(key: String): ConstantScoreQueryBuilder =
    new ConstantScoreQueryBuilder(
      new BoolQueryBuilder().filter(
        QueryBuilders.matchQuery(field, key).analyzer("portal_synonym")
      )
    )
}

class SynonymConstBaseTitleSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends SynonymConstBaseSearcher(host, port0, port1, protocol, "title")

class SynonymConstBaseContentSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends SynonymConstBaseSearcher(host, port0, port1, protocol, "content")

class SynonymConstBaseImageSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends ConstBaseSearcher(host, port0, port1, protocol, "image")

class SynonymConstBaseAttachedSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends ConstBaseSearcher(host, port0, port1, protocol, "attached")
