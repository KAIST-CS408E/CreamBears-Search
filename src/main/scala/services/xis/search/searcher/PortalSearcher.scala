package services.xis.search.searcher

import scala.collection.mutable.{Map => MMap}

import org.elasticsearch.client.RequestOptions
import org.elasticsearch.action.get.GetRequest
import org.elasticsearch.action.search.SearchResponse

import services.xis.search.SearchFormatter

import services.xis.crawl.ConnectUtil.Cookie
import services.xis.crawl.LoginUtil.login
import services.xis.crawl.SearchUtil

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

  override def searchAsString(
    formatter: SearchFormatter, index: String, typ: String, key: String
  ): Either[String, String] = {
    implicit val cookie: Cookie = MMap()
    lazy val pages: Stream[Int] = 1 #:: pages.map(_ + 1)
    login
    val res = pages
      .map(SearchUtil.search(key, _))
      .takeWhile(_.nonEmpty)
      .flatten
    Right(s"${res.mkString("\n")}\n${res.length}")
  }
}
