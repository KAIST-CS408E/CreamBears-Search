package services.xis.search.score

import Console.{BLUE, GREEN, RED, RESET}

final case class AnalyzeResult(
  ids: List[(String, Boolean)],
  missing: Set[String],
  keyToLink: Map[String, String]
) {
  override def toString: String =
    s"$BLUE[RESPONSE]$RESET\n" +
    ids.zipWithIndex
      .map{ case ((i, r), j) =>
              s"${if (r) GREEN else RED}${j + 1}. ${keyToLink(i)}$RESET" }
      .mkString("\n") +
    s"\n$BLUE[MISSING]$RED\n" +
    missing.toList.zipWithIndex
      .map{ case (i, j) => s"${j + 1}. ${keyToLink(i)}" }
      .mkString("\n") +
    RESET
}
