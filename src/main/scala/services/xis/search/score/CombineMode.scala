package services.xis.search.score

sealed abstract class CombineMode {
  val name: String
  def combine(scores: Iterable[Double]): Double
}

case object Min extends CombineMode {
  val name: String = "Min"
  def combine(scores: Iterable[Double]): Double = scores.min
}

case object Max extends CombineMode {
  val name: String = "Max"
  def combine(scores: Iterable[Double]): Double = scores.max
}

case object ArithmeticMean extends CombineMode {
  val name: String = "ArithmeticMean"
  def combine(scores: Iterable[Double]): Double = scores.sum / scores.size
}

case object GeometricMean extends CombineMode {
  val name: String = "GeometricMean"
  def combine(scores: Iterable[Double]): Double =
    math.pow(scores.product, 1.0 / scores.size)
}

case object HarmonicMean extends CombineMode {
  val name: String = "HarmonicMean"
  def combine(scores: Iterable[Double]): Double =
    scores.size / scores.map(1 / _).sum
}

object CombineMode extends ModeCompanion {
  type T = CombineMode

  val mods: List[CombineMode] = List(
    Min,
    Max,
    ArithmeticMean,
    GeometricMean,
    HarmonicMean,
  )
}
