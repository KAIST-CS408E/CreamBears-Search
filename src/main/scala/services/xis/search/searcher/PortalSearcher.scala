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

  private val boards = Set("it_newsletter", "notice", "International",
    "international_opportunities", "computer_network", "influenza_a_h1n1",
    "rnd_notices", "manuals_forms", "seminar_events", "student_notice",
    "lecture_academic_paper", "leadership_intern_counseling",
    "dormitory_notice", "dormitory_scholarship_welfare",
    "parttime_scholarship", "academic_courses", "recruiting",
    "gsc_usc_notice", "student_club", "researcher_on_military_duty",
    "classified", "work_notice", "today_notice")
  private val start = "20180801"
  private val end = "20181031"

  private def _search(key: String): Stream[SearchResult] = {
    implicit val cookie: Cookie = MMap()
    lazy val pages: Stream[Int] = 1 #:: pages.map(_ + 1)
    login
    pages
      .map(SearchUtil.search(key, start, end, _))
      .takeWhile(_.nonEmpty)
      .flatten
      .filter(a => boards(a.board))
  }

  override def searchAsIds(
    scroll: Boolean, index: String, typ: String, key: String
  ): List[String] = {
    _search(key).map(_.id).toList
  }

  override def searchAsString(
    formatter: SearchFormatter, scroll: Boolean,
    index: String, typ: String, key: String
  ): Either[String, String] = {
    val res = _search(key)
    val str = res.zipWithIndex.map{ case (a, i) =>
      s"${i + 1}. ${a.title}\tportal.kaist.ac.kr/ennotice/${a.board}/${a.id}"
    }.mkString("\n")
    Right(str)
  }
}
