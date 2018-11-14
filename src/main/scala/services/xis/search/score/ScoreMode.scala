package services.xis.search.score

sealed abstract class ScoreMode {
  val name: String

  def score(res: List[String], rel: Set[String]): ScoreResult = {
    val ress = res.toSet
    val sc = score1(res, ress, rel)
    val missing = rel &~ ress
    val unrel = ress &~ rel
    ScoreResult(sc, missing, unrel)
  }

  protected[score] def score1(
    res: List[String], ress: Set[String], rel: Set[String]
  ): Double
}

case object Precision extends ScoreMode {
  val name: String = "Precision"

  protected[score] def score1(
    res: List[String], ress: Set[String], rel: Set[String]
  ): Double =
    if (ress.isEmpty) 0 else (ress & rel).size.toDouble / ress.size
}

case object Recall extends ScoreMode {
  val name: String = "Recall"

  protected[score] def score1(
    res: List[String], ress: Set[String], rel: Set[String]
  ): Double =
    if (rel.isEmpty) 1 else (ress & rel).size.toDouble / rel.size
}

case object Fmeasure extends ScoreMode {
  val name: String = "Fmeasure"

  protected[score] def score1(
    res: List[String], ress: Set[String], rel: Set[String]
  ): Double = {
    val p = Precision.score1(res, ress, rel)
    val r = Recall.score1(res, ress, rel)
    if (p == 0 && r == 0) 0 else (2 * p * r) / (p + r)
  }
}

final case class PrecisionAt(k: Int) extends ScoreMode {
  val name: String = s"PrecisionAt$k"

  protected[score] def score1(
    res: List[String], _ress: Set[String], rel: Set[String]
  ): Double =
    if (res.isEmpty) 0
    else {
      val ress = res.take(k).toSet
      (ress & rel).size.toDouble / ress.size
    }
}

case object AveragePrecision extends ScoreMode {
  val name: String = "AveragePrecision"

  protected[score] def score1(
    res: List[String], ress: Set[String], rel: Set[String]
  ): Double =
    if (rel.isEmpty) {
      if (res.isEmpty) 1 else 0
    } else
      (for (k <- 1 to (rel.size min res.size))
         yield (
           if (rel(res(k - 1)))
             PrecisionAt(k).score1(res, ress, rel)
           else 0
         )
      ).sum / rel.size
}

case object NormalizedDiscountedCumulativeGain extends ScoreMode {
  val name: String = "NormalizedDiscountedCumulativeGain"

  private def log2(i: Int): Double = math.log(i) / math.log(2)

  protected[score] def score1(
    res: List[String], ress: Set[String], rel: Set[String]
  ): Double = {
    val dcg =
      (for (k <- 1 to (rel.size min res.size))
         yield (if (rel(res(k - 1))) 1 / log2(k + 1) else 0)).sum
    val idcg = 
      (for (k <- 1 to rel.size) yield (1 / log2(k + 1))).sum
    dcg / idcg
  }
}

case object ReciprocalRank extends ScoreMode {
  val name: String = "ReciprocalRank"

  protected[score] def score1(
    res: List[String], ress: Set[String], rel: Set[String]
  ): Double = {
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
