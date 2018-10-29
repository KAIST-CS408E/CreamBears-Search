package services.xis.search.searcher

import java.io.IOException

import org.apache.http.HttpHost

import org.elasticsearch.client.{RestHighLevelClient, RestClient}
import org.elasticsearch.action.search.SearchResponse

abstract class Searcher(
  host: String,
  port0: Int,
  port1: Int,
  protocol: String
) {
  val client: RestHighLevelClient = 
    new RestHighLevelClient(RestClient.builder(
      new HttpHost(host, port0, protocol),
      new HttpHost(host, port1, protocol)
    ))
  def close(): Unit = client.close()
  def printSearch(index: String, typ: String, key: String): Unit = {
    search(index, typ, key).getHits.getHits.foreach(h => {
      val s = h.getSourceAsMap
      println(h.getId)
      println(s"${s.get("board")}/${h.getId}")
      List("title", "content", "attached", "image").foreach{k =>
        println(s"$k: ${s.get(k)}")
      }
      println()
    })
  }
  @throws(classOf[IOException])
  def search(index: String, typ: String, key: String): SearchResponse
}
