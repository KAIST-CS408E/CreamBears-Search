package services.xis.search

import scala.collection.mutable.StringBuilder

import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.search.SearchHit

class SearchFormatter(_format: String) {

  private def hits(response: SearchResponse): List[(SearchHit, Int)] =
    response.getHits.getHits.toList.zipWithIndex

  def rawFormatResponse(response: SearchResponse): List[String] =
    hits(response).flatMap(rawParseAndFill)

  def formatResponse(response: SearchResponse): Either[String, String] = {
    response.getHits.getHits.toList.zipWithIndex match {
      case h :: t =>
        val hd = parseAndFill(h)
        hd match {
          case Left('\n') =>
            Left("\\ at the end of the format")
          case Left(ch) => 
            Left(s"Unknown escape sequence: \\$ch")
          case Right(res) =>
            Right(s"$res\n${t.map(parseAndFill(_).merge).mkString("\n")}")
        }
      case Nil => 
        Left("No result")
    }
  }

  private val format = _format.toList

  private def rawParseAndFill(p: (SearchHit, Int)): Option[String] =
    parseAndFill(p).toOption

  private def parseAndFill(p: (SearchHit, Int)): Either[Char, String] =
    p match {
      case (h, ind) =>
        val src = h.getSourceAsMap
        def get(s: String): String = src.get(s).toString
        val map = Map[Char, String](
          '\\' -> "\\",
          'N' -> (ind + 1).toString,
          'S' -> h.getScore.toString,
          'E' -> h.getExplanation.toString,
          'I' -> h.getId,
          'B' -> get("board"),
          'T' -> get("title"),
          'A' -> get("author"),
          'd' -> get("department"),
          't' -> get("time"),
          'h' -> get("hits"),
          'c' -> get("content"),
          'a' -> get("attached"),
          'i' -> get("image")
        )

        def aux(
          builder: StringBuilder, seq: List[Char],
          escaped: Boolean, field: Option[String] = None, length: Int = 0
        ): Either[Char, String] = seq match {
          case hd :: tl =>
            if (escaped) {
              field match {
                case f @ Some(s) =>
                  if (hd.isDigit)
                    aux(builder, tl, true, f, length * 10 + hd.asDigit)
                  else {
                    aux(builder ++=
                      (if (length == 0 || length > s.length) s
                       else s.substring(0, length)),
                      seq, false)
                  }
                case None => map.get(hd) match {
                  case None => Left(hd)
                  case f => aux(builder, tl, true, f)
                }
              }
            } else {
              if (hd == '\\')
                aux(builder, tl, true)
              else
                aux(builder += hd, tl, false)
            }
          case Nil =>
            if (escaped) field match {
              case Some(s) =>
                builder ++= (if (length == 0 || length > s.length) s
                             else s.substring(0, length))
                Right(builder.toString)
              case None => Left('\n')
            } else
              Right(builder.toString)
        }

        aux(new StringBuilder(), format, false)
    }
}

object SearchFormatter {
  val default = new SearchFormatter(
"""\N(\S). portal.kaist.ac.kr/ennotice/\B/\I
[\T](\t)
Content: \c80
Image: \i80
Attach: \a80
\E
""")
  val idFormatter = new SearchFormatter("\\I")
  val html = new SearchFormatter(
"""<div class="card"><div class="card-body"><a href="https://portal.kaist.ac.kr/ennotice/\B/\I" target="_blank" onclick="read(event)">\T</a><p>\c100</p></div></div>"""
  )
}
