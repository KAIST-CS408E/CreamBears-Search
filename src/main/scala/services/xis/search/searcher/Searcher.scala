package services.xis.search.searcher

import java.io.IOException

import org.apache.http.HttpHost

import org.elasticsearch.client.{RestHighLevelClient, RestClient, RequestOptions}
import org.elasticsearch.action.search.{SearchResponse, SearchScrollRequest}

import services.xis.search.SearchFormatter

abstract class Searcher(
  host: String,
  port0: java.lang.Integer,
  port1: java.lang.Integer,
  protocol: String
) {
  val client: RestHighLevelClient = 
    new RestHighLevelClient(RestClient.builder(
      new HttpHost(host, port0, protocol),
      new HttpHost(host, port1, protocol)
    ))

  def close(): Unit = client.close()

  def scrollSearchAsIds(
    index: String, typ: String, key: String
  ): List[String] = {
    lazy val responses: Stream[SearchResponse] =
      search(index, typ, key) #::
        responses.map(r => {
          val request = new SearchScrollRequest(r.getScrollId).scroll("30s")
          client.scroll(request, RequestOptions.DEFAULT)
        })
    responses
      .map(SearchFormatter.idFormatter.rawFormatResponse)
      .takeWhile(_.nonEmpty)
      .toList
      .flatten
  }

  def searchAsString(
    formatter: SearchFormatter, index: String, typ: String, key: String
  ): Either[String, String] = {
    val response = search(index, typ, key)
    formatter.formatResponse(response)
  }

  def searchAsIds(
    index: String, typ: String, key: String
  ): List[String] = {
    val response = search(index, typ, key)
    SearchFormatter.idFormatter.rawFormatResponse(response)
  }

  @throws(classOf[IOException])
  def search(index: String, typ: String, key: String): SearchResponse
}
