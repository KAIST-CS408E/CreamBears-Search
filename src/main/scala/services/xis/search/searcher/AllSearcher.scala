package services.xis.search.searcher

import org.elasticsearch.search.SearchHit
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.action.search.{SearchRequest, SearchScrollRequest}
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.action.search.SearchResponse

import services.xis.search.SearchFormatter

class AllSearcher(
  host: String,
  port0: java.lang.Integer,
  port1: java.lang.Integer,
  protocol: String
) extends Searcher(host, port0, port1, protocol) {
  override def searchAsString(
    formatter: SearchFormatter, index: String, typ: String, key: String
  ): Either[String, String] = {
    val response = search(index, typ, key)
    val hits = response.getHits.getHits

    def aux(
      id: String, hitss: List[Array[SearchHit]]
    ): List[Array[SearchHit]] = {
      val request = new SearchScrollRequest(id).scroll("30s")
      val response = client.scroll(request, RequestOptions.DEFAULT);
      val id_ = response.getScrollId
      val hits = response.getHits.getHits
      if (hits.nonEmpty) aux(id_, hits :: hitss) else hitss
    }
    val hitss = aux(response.getScrollId, List(hits))

    Right(
      hitss.flatten.map(h => {
        val s = h.getSourceAsMap
        s"${s.get("title")}\n${s.get("content")}"
      }).mkString("\n")
    )
  }

  def search(index: String, typ: String, key: String): SearchResponse = {
    val request = new SearchRequest()
      .source(
        new SearchSourceBuilder()
          .query(QueryBuilders.matchAllQuery)
          .size(100)
      )
      .scroll("60m")
    client.search(request, RequestOptions.DEFAULT)
  }
}
