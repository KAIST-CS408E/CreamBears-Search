package services.xis.search.searcher

import org.elasticsearch.client.RequestOptions
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.search.builder.SearchSourceBuilder

class NaiveTitleSearcher(
  host: String,
  port0: java.lang.Integer,
  port1: java.lang.Integer,
  protocol: String,
  index: String,
  typ: String
) extends Searcher(
  host, port0, port1, protocol
) {
  def search(key: String): Unit = {
    val request = new SearchRequest(index).types(typ)
    val builder = new SearchSourceBuilder()
    builder.query(QueryBuilders.matchQuery("title", key))
    request.source(builder)
    val response = client.search(request, RequestOptions.DEFAULT)
    response.getHits.getHits.foreach(h => {
      val s = h.getSourceAsMap
      println(h.getId)
      println(s.get("board"))
      println(s.get("title"))
      println(s.get("content"))
      println()
    })
  }
}
