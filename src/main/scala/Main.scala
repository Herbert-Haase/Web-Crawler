package de.htwg.webcrawler {

  import de.htwg.webcrawler.TUI.*

  @main def run(): Unit = {
    val width = 80
    val height = 100
    val file = "./src/main/scala/TUI.scala"
    val lines = wrapFileContents(file, width)
    println(buildWindowString(height, width, lines))
  }
}
