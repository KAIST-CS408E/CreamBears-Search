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

  private def scrollResponse(response: SearchResponse): Stream[SearchResponse] = {
    lazy val responses: Stream[SearchResponse] =
      response #::
        responses.map(r => {
          val request = new SearchScrollRequest(r.getScrollId).scroll("30s")
          client.scroll(request, RequestOptions.DEFAULT)
        })
    responses
  }

  def searchAsString(
    formatter: SearchFormatter, scroll: Boolean,
    index: String, typ: String, key: String
  ): Either[String, String] = {
    val response = search(index, typ, key)
    val responses = if (scroll) scrollResponse(response) else Stream(response)
    val formatted = responses.map(formatter.formatResponse)
    formatted.head match {
      case l: Left[_, _] => l
      case _ =>
        Right(formatted.takeWhile(_.isRight).map(_.merge).mkString("\n"))
    }
  }

  def searchAsIds(
    scroll: Boolean, index: String, typ: String, key: String
  ): List[String] = {
    val response = search(index, typ, key)
    val responses = if (scroll) scrollResponse(response) else Stream(response)
    responses
      .map(SearchFormatter.idFormatter.rawFormatResponse)
      .takeWhile(_.nonEmpty)
      .toList
      .flatten
  }

  def searchPageAsIds(
    index: String, typ: String, key: String, page: Int
  ): List[String] =
    SearchFormatter.idFormatter.rawFormatResponse(
      searchPage(index, typ, key, page)
    )

  @throws(classOf[IOException])
  def searchPage(index: String, typ: String, key: String, page: Int): SearchResponse

  @throws(classOf[IOException])
  def search(index: String, typ: String, key: String): SearchResponse
}
