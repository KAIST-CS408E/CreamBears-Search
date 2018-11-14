package services.xis.search.score

final case class ScoreResult(
  score: Double,
  missing: Set[String],
  unrelevant: Set[String]
) {
  override def toString: String = score.toString
}
