package services.xis.search

import services.xis.search.searcher.Searcher

object Api {
  def getSearcher(name: String): Option[Searcher] =
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

  def idsToHtml(ids: List[String], index: String, typ: String): String = {
    val getter = getSearcher("Getter").get
    val res = ids
      .map(getter.searchAsString(SearchFormatter.html, false, index, typ, _).merge)
      .mkString("\n")
    getter.close()
    res
  }
}
