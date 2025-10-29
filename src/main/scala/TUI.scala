<<<<<<< Updated upstream
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
    // development
    // development2
=======
import scala.io.Source
import java.io.File

@main def tui(s: Int): Unit = {
  val filename = "./TUI.scala"
  print_window(s, filename)
}

def print_fromFile(line_width: Int, filename: File): String = {
  val leftover_w = False
  var result = ""
  for (line <- Source.fromFile(filename).getLines) {
    val words = line.split("\\s+")
    // val line_str = words.mkString(" ")
    for (word <- words) {
      if (line_width <= words.len) {
        line_width -= words.len
        // concat word to line
      } else {
        leftover_w = True
        // concat \n to line
      }
    }
    if (leftover_w) {
      return text_line
    } else {
      return text_line + "\n"
    }
  }
}

def print_window(s: Int, file: File): Unit = {
  val width = s * (s - 1)
  val horizontal_line = "+" + "-" * width + "+"

  println(horizontal_line)

  for (i <- 0 until s * 3)
    println("|" + print_fromFile(width, file) + "|")

  println(horizontal_line)
>>>>>>> Stashed changes
}
