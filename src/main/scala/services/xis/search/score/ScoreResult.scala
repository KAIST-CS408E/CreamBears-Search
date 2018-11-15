package services.xis.search.score

final case class AnalyzeResult(
  ids: List[(String, Boolean)],
  missing: Set[String],
  keyToLink: Map[String, String]
) {
  override def toString: String =
    ids.zipWithIndex
      .map{ case ((i, r), j) =>
              s"${j + 1}. ${keyToLink(i)}\t${if (r) "" else "UNREL"}" }
      .mkString("\n") +
    "\nMISSING\n" +
    missing.zipWithIndex
      .map{ case (i, j) => s"${j + 1}. ${keyToLink(i)}" }
      .mkString("\n")
}
