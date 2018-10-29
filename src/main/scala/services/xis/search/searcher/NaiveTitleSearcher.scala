package services.xis.search.searcher

import org.elasticsearch.client.RequestOptions
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.action.search.SearchResponse

class NaiveTitleSearcher(
  host: String,
  port0: java.lang.Integer,
  port1: java.lang.Integer,
  protocol: String
) extends Searcher(
  host, port0, port1, protocol
) {
  def search(index: String, typ: String, key: String): SearchResponse = {
    val request = new SearchRequest(index).types(typ)
    val builder = new SearchSourceBuilder()
    builder.query(QueryBuilders.matchQuery("title", key))
    request.source(builder)
    client.search(request, RequestOptions.DEFAULT)
  }
}
