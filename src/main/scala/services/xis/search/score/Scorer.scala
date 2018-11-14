package services.xis.search.score

import scala.io.Source

class Scorer(name: String) {
  val (keywords, keyToRelevants): (Set[String], Map[String, Set[String]]) =
    {
      def getId(url: String): String = {
        val i = url.lastIndexOf("/")
        url.substring(i + 1)
      }
      val data = Source.fromFile(name).mkString.split("\n").map(_.split(","))
      val keywords = data.head.init
      val articles = data.tail
      val relevants = (0 until keywords.length)
        .map(i => i -> articles.filter(_(i) == "O").map(a => getId(a.last)).toSet)
      val keyToRel = relevants.map{ case (i, s) => keywords(i) -> s }
      (keywords.toSet, keyToRel.toMap)
    }

  def scoreFor(
    keyword: String, ids: List[String], mod: ScoreMode
  ): ScoreResult =
    mod.score(ids, keyToRelevants(keyword))

  def scoreAll(
    ids: Map[String, List[String]], mod: ScoreMode, cmod: CombineMode
  ): Double =
    cmod.combine(
      keywords.map(
        keyword => mod.score(ids(keyword), keyToRelevants(keyword)).score
      )
    )
}
