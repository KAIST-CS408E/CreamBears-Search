package services.xis.search.searcher

import scala.collection.mutable.{Map => MMap}

import org.elasticsearch.client.RequestOptions
import org.elasticsearch.action.get.GetRequest
import org.elasticsearch.action.search.SearchResponse

import services.xis.search.SearchFormatter

import services.xis.crawl.ConnectUtil.Cookie
import services.xis.crawl.LoginUtil.login
import services.xis.crawl.{SearchUtil, SearchResult}

class PortalSearcher(
  host: String,
  port0: java.lang.Integer,
  port1: java.lang.Integer,
  protocol: String
) extends Searcher(
  host, port0, port1, protocol
) {
  object ShouldNotBeCalledException extends Exception
  def search(index: String, typ: String, key: String): SearchResponse =
    throw ShouldNotBeCalledException

  private def _search(key: String): Stream[SearchResult] = {
    implicit val cookie: Cookie = MMap()
    lazy val pages: Stream[Int] = 1 #:: pages.map(_ + 1)
    login
    pages
      .map(SearchUtil.search(key, _))
      .takeWhile(_.nonEmpty)
      .flatten
  }

  override def scrollSearchAsIds(
    index: String, typ: String, key: String
  ): List[String] = searchAsIds(index, typ, key)

  override def searchAsIds(
    index: String, typ: String, key: String
  ): List[String] = {
    _search(key).map(_.id).toList
  }

  override def searchAsString(
    formatter: SearchFormatter, index: String, typ: String, key: String
  ): Either[String, String] = {
    val res = _search(key)
    Right(s"${res.mkString("\n")}\n${res.length}")
  }
}
