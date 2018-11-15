package services.xis.search.searcher

import java.lang.Integer

import org.elasticsearch.index.query.MatchQueryBuilder

trait SynonymMixin extends BaseSearcher {
  abstract override def builder(key: String): MatchQueryBuilder =
    super.builder(key).analyzer("portal_synonym")
}

class SynonymTitleSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends BaseTitleSearcher(host, port0, port1, protocol) with SynonymMixin

class SynonymContentSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends BaseContentSearcher(host, port0, port1, protocol) with SynonymMixin

class SynonymImageSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends BaseImageSearcher(host, port0, port1, protocol) with SynonymMixin

class SynonymAttachedSearcher(
  host: String, port0: Integer, port1: Integer, protocol: String
) extends BaseAttachedSearcher(host, port0, port1, protocol) with SynonymMixin
