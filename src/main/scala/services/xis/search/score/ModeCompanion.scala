package services.xis.search.score

abstract class ModeCompanion {
  type T <: { val name: String }

  val mods: List[T]

  private def editDist(a: String, b: String): Int =
    ((0 to b.size).toList /: a)((prev, x) =>
      (prev zip prev.tail zip b).scanLeft(prev.head + 1) {
        case (h, ((d, v), y)) =>
          math.min(math.min(h + 1, v + 1), d + (if (x == y) 0 else 1))
      }).last

  def findMode(name: String): T = {
    mods.find(_.name == name) match {
      case Some(mod) => mod
      case None =>
        System.err.println(s"Fail to find the mode:$name")
        val mod = mods.minBy(mod => editDist(mod.name, name))
        System.err.println(s"Instead, choose the mode:${mod.name}")
        mod
    }
  }
}
