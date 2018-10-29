package services.xis.search.searcher

import org.elasticsearch.client.RequestOptions
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.action.search.SearchResponse

abstract class GenBuilderSearcher(
  host: String,
  port0: java.lang.Integer,
  port1: java.lang.Integer,
  protocol: String
) extends Searcher(
  host, port0, port1, protocol
) {
  def search(index: String, typ: String, key: String): SearchResponse = {
    val request = new SearchRequest(index).types(typ)
    request.source(builder(key))
    client.search(request, RequestOptions.DEFAULT)
  }
  def builder(key: String): SearchSourceBuilder
}
