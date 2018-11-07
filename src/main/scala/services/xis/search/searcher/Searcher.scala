package services.xis.search.searcher

import java.io.IOException

import org.apache.http.HttpHost

import org.elasticsearch.client.{RestHighLevelClient, RestClient}
import org.elasticsearch.action.search.SearchResponse

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

  def searchAsString(
    formatter: SearchFormatter, index: String, typ: String, key: String
  ): Either[String, String] = {
    val response = search(index, typ, key)
    formatter.formatResponse(response)
  }

  @throws(classOf[IOException])
  def search(index: String, typ: String, key: String): SearchResponse
}
