package services.xis.search.score

sealed abstract class ScoreMode {
  val name: String

  def score(res: List[String], rel: Set[String]): Double
}

case object Precision extends ScoreMode {
  val name: String = "Precision"

  def score(res: List[String], rel: Set[String]): Double =
    if (res.isEmpty) 0 else (res.toSet & rel).size.toDouble / res.size
}

case object Recall extends ScoreMode {
  val name: String = "Recall"

  def score(res: List[String], rel: Set[String]): Double =
    if (rel.isEmpty) 1 else (res.toSet & rel).size.toDouble / rel.size
}

case object Fmeasure extends ScoreMode {
  val name: String = "Fmeasure"

  def score(res: List[String], rel: Set[String]): Double = {
    val p = Precision.score(res, rel)
    val r = Recall.score(res, rel)
    if (p == 0 && r == 0) 0 else (2 * p * r) / (p + r)
  }
}

final case class PrecisionAt(k: Int) extends ScoreMode {
  val name: String = s"PrecisionAt$k"

  def score(res: List[String], rel: Set[String]): Double =
    if (res.isEmpty) 0
    else {
      val ress = res.take(k).toSet
      (ress & rel).size.toDouble / ress.size
    }
}

case object AveragePrecision extends ScoreMode {
  val name: String = "AveragePrecision"

  def score(res: List[String], rel: Set[String]): Double =
    if (rel.isEmpty) {
      if (res.isEmpty) 1 else 0
    } else
      (for (k <- 1 to res.length)
         yield (if (rel(res(k - 1))) PrecisionAt(k).score(res, rel) else 0)
      ).sum / rel.size
}

case object NormalizedDiscountedCumulativeGain extends ScoreMode {
  val name: String = "NormalizedDiscountedCumulativeGain"

  private def log2(i: Int): Double = math.log(i) / math.log(2)

  def score(res: List[String], rel: Set[String]): Double =
    if (rel.isEmpty) {
      if (res.isEmpty) 1 else 0
    } else {
      val dcg =
        (for (k <- 1 to res.size)
           yield (if (rel(res(k - 1))) 1 / log2(k + 1) else 0)).sum
      val idcg = 
        (for (k <- 1 to rel.size) yield (1 / log2(k + 1))).sum
      dcg / idcg
    }
}

case object ReciprocalRank extends ScoreMode {
  val name: String = "ReciprocalRank"

  def score(res: List[String], rel: Set[String]): Double = {
    val ind = res.indexWhere(rel(_), 0)
    if (ind == -1) 0 else 1.0 / (ind + 1)
  }
}

object ScoreMode extends ModeCompanion {
  type T = ScoreMode

  val mods: List[ScoreMode] = List(
    Precision,
    Recall,
    Fmeasure,
    AveragePrecision,
    NormalizedDiscountedCumulativeGain,
    ReciprocalRank
  )
}
