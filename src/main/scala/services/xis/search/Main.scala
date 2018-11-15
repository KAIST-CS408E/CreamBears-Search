package services.xis.search

import scala.io.Source
import Console.{BLUE, CYAN, GREEN, RED, RESET}

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
          val portal = getSearcher("PortalSearcher").get
          val pids = portal.searchAsIds(true, index, typ, key)
          portal.close()

          val ids = searcher.searchAsIds(true, index, typ, key)
          val scorer = new Scorer(label)
          println(scorer.analyze(key, ids))
          println(s"$BLUE[SCORES]$RESET")
          for (mod <- ScoreMode.mods) {
            val res = scorer.scoreFor(key, ids, mod)
            val pres = scorer.scoreFor(key, pids, mod)
            print(s"$CYAN[${mod.name.take(5).toUpperCase}]$RESET")
            val sym = if (res > pres) ">" else if (res == pres) "=" else "<"
            val color =
              if (res > pres) GREEN else if (res == pres) RESET else RED
            println(f" $color$res%.5f $sym $pres%.5f$RESET")
          }
        } finally {
          searcher.close()
        }
    case "--scroll" :: index :: name :: key :: fileOpt =>
      for (searcher <- getSearcher(name))
        try {
          val formatter = getFormatter
          val res = trySearch(searcher, formatter, true, index, typ, key)
          res.fold(System.err.println, printResult(_, fileOpt))
        } finally {
          searcher.close()
        }
    case index :: name :: key :: fileOpt =>
      for (searcher <- getSearcher(name))
        try {
          val formatter = getFormatter
          val res = trySearch(searcher, formatter, false, index, typ, key)
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
    searcher: Searcher, formatter: SearchFormatter, scroll: Boolean,
    index: String, typ: String, key: String
  ): Either[String, String] =
    try {
      searcher.searchAsString(formatter, scroll, index, typ, key)
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
