package services.xis.search.searcher

import org.apache.http.HttpHost

import org.elasticsearch.client.{RestHighLevelClient, RestClient}

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
  def search(key: String): Unit
}
