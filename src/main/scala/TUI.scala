package de.htwg.webcrawler {
  object TUI
  def main(args: Array[String]): Unit =
    print_window(args(0).toInt)

  def print_window(s: Int): Unit =
    val width = s * (s - 1)
    val horizontal_line = "+" + "-" * width + "+"

    println(horizontal_line)

    for (i <- 0 until s * 3)
      println("|" + " " * width + "|")

    println(horizontal_line)
    // experimental

}
