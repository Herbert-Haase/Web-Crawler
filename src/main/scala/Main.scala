package de.htwg.webscraper {

  import de.htwg.webscraper

  class Main
  def main(args:Array[String]): Unit = {
    if (args.length < 2) {
    println("Error: Please provide two arguments for width and height.")
    return
    }
    val x:Int = args(0).toInt
    val y:Int = args(1).toInt
    val file = "./src/main/scala/TUI.scala"
    if (x < 1|| y < 1) {
      println("Error")
    } else {
      val myTui: Tui = new Tui(x, y, file)
      myTui.print_Tui()
    }
  }
}