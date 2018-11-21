package services.xis.search.searcher

import org.elasticsearch.search.SearchHit
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.action.search.{SearchRequest, SearchScrollRequest}
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.action.search.SearchResponse

import services.xis.search.SearchFormatter

class AllSearcher(
  host: String,
  port0: java.lang.Integer,
  port1: java.lang.Integer,
  protocol: String
) extends Searcher(host, port0, port1, protocol) {
  def searchPage(index: String, typ: String, key: String, page: Int): SearchResponse =
    search(index, typ, key)

  def search(index: String, typ: String, key: String): SearchResponse = {
    val request = new SearchRequest(index)
      .types(typ)
      .source(
        new SearchSourceBuilder()
          .query(QueryBuilders.matchAllQuery)
          .explain(true)
          .size(100)
      )
      .scroll("60m")
    client.search(request, RequestOptions.DEFAULT)
  }
}
