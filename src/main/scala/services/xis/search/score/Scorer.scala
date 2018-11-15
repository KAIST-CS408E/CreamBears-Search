package services.xis.search.score

import scala.io.Source

class Scorer(name: String) {
  val (
    keywords, keyToRelevants, keyToLink
  ): (Set[String], Map[String, Set[String]], Map[String, String]) =
    {
      def getId(url: String): String = {
        val i = url.lastIndexOf("/")
        url.substring(i + 1).filter(_.isDigit)
      }
      val data = Source.fromFile(name).mkString.split("\n").map(_.split(","))
      val keywords = data.head.init
      val articles = data.tail
      val relevants =
        (0 until keywords.length)
          .map(i => i -> (
            articles
              .filter(a => a(i).nonEmpty && a(i).last == 'O')
              .map(a => getId(a.last))
              .toSet
          ))
      val keyToRel = relevants.map{ case (i, s) => keywords(i) -> s }
      val keyToLink = articles.map(a => {
        val url = a.last
        getId(url) -> url.init
      })
      (keywords.toSet, keyToRel.toMap, keyToLink.toMap)
    }

  def analyze(
    keyword: String, ids: List[String]
  ): AnalyzeResult = {
    val res = keyToRelevants(keyword)
    AnalyzeResult(
      ids.map(i => (i, res(i))),
      res &~ ids.toSet,
      keyToLink
    )
  }

  def scoreFor(
    keyword: String, ids: List[String], mod: ScoreMode
  ): Double = mod.score(ids, keyToRelevants(keyword))

  def scoreAll(
    ids: Map[String, List[String]], mod: ScoreMode, cmod: CombineMode
  ): Double =
    cmod.combine(
      keywords.map(
        keyword => mod.score(ids(keyword), keyToRelevants(keyword))
      )
    )
}
