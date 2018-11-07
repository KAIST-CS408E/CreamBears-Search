package services.xis.search

import scala.io.Source

import java.io.{FileOutputStream, IOException}

import services.xis.search.searcher.Searcher

object Main {

  private val params = List(
    "localhost",
    new Integer(9200),
    new Integer(9300),
    "http"
  )
  private val index = "portal3"
  private val typ = "article"

  def main(args: Array[String]): Unit = args.toList match {
    case name :: key :: fileOpt =>
     try {
       val packageName = this.getClass.getPackage.getName
       val subPackageName = "searcher"
       val c = Class.forName(s"$packageName.$subPackageName.$name")
         .asInstanceOf[Class[Searcher]]
       val cons = c.getConstructor(params.map(_.getClass): _*)

       val formatter =
         try {
           new SearchFormatter(Source.fromFile("format").mkString)
         } catch {
           case _: IOException => SearchFormatter.default
         }
       val searcher = cons.newInstance(params: _*)
       val res = 
         try {
           searcher.searchAsString(formatter, index, typ, key)
         } catch {
           case e: Exception => Left(e.getMessage)
         } finally {
           searcher.close()
         }

       res match {
         case Right(res) =>
           fileOpt match {
             case file :: Nil =>
               val out = new FileOutputStream(file)
               Console.withOut(out) { println(res) }
               out.close()
             case _ => println(res)
           }
         case Left(err) =>
           System.err.println(err)
       }
     } catch {
       case _: ClassNotFoundException => println("Class not found")
     }
    case _ => println("Usage: run [class] [keyword]")
  }
}
