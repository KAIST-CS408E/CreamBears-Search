package services.xis.search

import scala.io.Source

import java.io.{FileOutputStream, IOException}

import services.xis.search.searcher.Searcher
import services.xis.search.score.{Scorer, ScoreMode}

object Main {

  private val typ = "article"
  private val help =
    """Usage: run [index] [class] [keyword]
      |       run --score [label] [index] [class] [keyword]""".stripMargin

  def main(args: Array[String]): Unit = args.toList match {
    case "--score" :: label :: index :: name :: key :: fileOpt =>
      for (searcher <- getSearcher(name)) 
        try {
          val ids = searcher.searchAsIds(true, index, typ, key)
          val scorer = new Scorer(label)
          for (mod <- ScoreMode.mods) {
            val res = scorer.scoreFor(key, ids, mod)
            println(s"${mod.name}\n$res")
          }
        } finally {
          searcher.close()
        }
    case index :: name :: key :: fileOpt =>
      for (searcher <- getSearcher(name))
        try {
          val formatter = getFormatter
          val res = trySearch(searcher, formatter, index, typ, key)
          res.fold(System.err.println, printResult(_, fileOpt))
        } finally {
          searcher.close()
        }
    case _ => println(help)
  }

  private def getSearcher(name: String): Option[Searcher] =
    try {
      val params =
        List("localhost", new Integer(9200), new Integer(9300), "http")
      val packageName = this.getClass.getPackage.getName
      val subPackageName = "searcher"
      val c = Class.forName(s"$packageName.$subPackageName.$name")
        .asInstanceOf[Class[Searcher]]
      val cons = c.getConstructor(params.map(_.getClass): _*)
      Some(cons.newInstance(params: _*))
    } catch {
      case _: ClassNotFoundException =>
        System.err.println(s"Class not found: $name")
        None
    }

  private def getFormatter: SearchFormatter =
    try {
      new SearchFormatter(Source.fromFile("format").mkString)
    } catch {
      case _: IOException => SearchFormatter.default
    }

  private def trySearch(
    searcher: Searcher, formatter: SearchFormatter,
    index: String, typ: String, key: String
  ): Either[String, String] =
    try {
      searcher.searchAsString(formatter, false, index, typ, key)
    } catch {
      case e: Exception => Left(e.getMessage)
    }

  private def printResult(res: String, fileOpt: List[String]): Unit =
    fileOpt match {
      case file :: Nil =>
        val out = new FileOutputStream(file)
        Console.withOut(out) { println(res) }
        out.close()
      case _ => println(res)
    }
}
