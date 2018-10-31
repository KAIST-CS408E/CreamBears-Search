package services.xis.search.searcher

import org.elasticsearch.client.RequestOptions
import org.elasticsearch.action.get.GetRequest
import org.elasticsearch.action.search.SearchResponse

import services.xis.search.SearchFormatter

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

  override def searchAsString(
    formatter: SearchFormatter, index: String, typ: String, id: String
  ): Either[String, String] = {
    val request = new GetRequest(index, typ, id)
    Right(client.get(request, RequestOptions.DEFAULT).toString)
  }
}
