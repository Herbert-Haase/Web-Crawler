case class Cell(value:Int) {
 def isSet:Boolean = value != 0
}

val cell1 = Cell(2)
// cell1: Cell = Cell(2)
cell1.isSet

val cell2 = Cell(0)
// cell2: Cell = Cell(0)
cell2.isSet

case class Field(cells: Array[Cell])

val field1 = Field(Array.ofDim[Cell](1))
// field1: Field = Field([Lrepl.MdocSession$MdocApp$Cell;@7ab1b46c)
field1.cells(0) = cell1

case class House(cells:Vector[Cell])

val house = House(Vector(cell1, cell2))
// house: House = House(Vector(Cell(2), Cell(0)))

house.cells(0).value
// res3: Int = 2
house.cells(0).isSet
// res4: Boolean = true
