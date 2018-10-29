package services.xis.search

import services.xis.search.searcher.Searcher

object Main {

  private val params = List(
    "localhost",
    new Integer(9200),
    new Integer(9300),
    "http"
  )
  private val index = "portal2"
  private val typ = "article"

  def main(args: Array[String]): Unit = args.toList match {
    case name :: key :: Nil =>
     try {
       val packageName = this.getClass.getPackage.getName
       val subPackageName = "searcher"
       val c = Class.forName(s"$packageName.$subPackageName.$name")
         .asInstanceOf[Class[Searcher]]
       val cons = c.getConstructor(params.map(_.getClass): _*)
       val searcher = cons.newInstance(params: _*)
       searcher.printSearch(index, typ, key)
       searcher.close()
     } catch {
       case _: ClassNotFoundException => println("Class not found")
     }
    case _ => println("Usage: run [class] [keyword]")
  }
}
