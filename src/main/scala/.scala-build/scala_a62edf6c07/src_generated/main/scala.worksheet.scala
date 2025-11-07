

final class scala$u002Eworksheet$_ {
def args = scala$u002Eworksheet_sc.args$
def scriptPath = """scala.worksheet.sc"""
/*<script>*/
case class Cell(value: Int) {
  def isSet: Boolean = value != 0
}

val cell1 = Cell(2)
// cell1: Cell = Cell(2)
cell1.isSet
// res0: Boolean = true

val cell2 = Cell(0)
// cell2: Cell = Cell(0)
cell2.isSet
// res1: Boolean = false

case class Field(cells: Array[Cell])

val field1 = Field(Array.ofDim[Cell](1))
// field1: Field = Field([Lrepl.MdocSession$MdocApp$Cell;@7ab1b46c)
field1.cells(0) = cell1

case class House(cells: Vector[Cell])

val house = House(Vector(cell1, cell2))
// house: House = House(Vector(Cell(2), Cell(0)))

house.cells(0).value
// res3: Int = 2
house.cells(0).isSet
// res4: Boolean = true

/*</script>*/ /*<generated>*//*</generated>*/
}

object scala$u002Eworksheet_sc {
  private var args$opt0 = Option.empty[Array[String]]
  def args$set(args: Array[String]): Unit = {
    args$opt0 = Some(args)
  }
  def args$opt: Option[Array[String]] = args$opt0
  def args$: Array[String] = args$opt.getOrElse {
    sys.error("No arguments passed to this script")
  }

  lazy val script = new scala$u002Eworksheet$_

  def main(args: Array[String]): Unit = {
    args$set(args)
    val _ = script.hashCode() // hashCode to clear scalac warning about pure expression in statement position
  }
}

export scala$u002Eworksheet_sc.script as `scala.worksheet`

