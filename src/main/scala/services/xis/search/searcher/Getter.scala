package services.xis.search.searcher

import org.elasticsearch.client.RequestOptions
import org.elasticsearch.action.get.GetRequest

class Getter(
  host: String,
  port0: java.lang.Integer,
  port1: java.lang.Integer,
  protocol: String,
  index: String,
  typ: String
) extends Searcher(
  host, port0, port1, protocol
) {
  def search(id: String): Unit = {
    val request = new GetRequest(index, typ, id)
    println(client.get(request, RequestOptions.DEFAULT))
  }
}
