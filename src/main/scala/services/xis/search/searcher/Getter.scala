package services.xis.search.searcher

import org.elasticsearch.client.RequestOptions
import org.elasticsearch.action.get.GetRequest
import org.elasticsearch.action.search.SearchResponse

class Getter(
  host: String,
  port0: java.lang.Integer,
  port1: java.lang.Integer,
  protocol: String
) extends Searcher(
  host, port0, port1, protocol
) {
  object ShouldNotBeCalledException extends Exception
  def search(index: String, typ: String, id: String): SearchResponse =
    throw ShouldNotBeCalledException

  override def printSearch(index: String, typ: String, id: String): Unit = {
    val request = new GetRequest(index, typ, id)
    println(client.get(request, RequestOptions.DEFAULT))
  }
}
