package de.htwg.webscraper

import java.io.{File, PrintWriter}

class InteractiveTui(width: Int, height: Int) {

  def run(): Unit = {
    println("Please enter your text. Press Enter on an empty line to finish.")
    val lines = readMultilineInput()

    val tempFile = File.createTempFile("interactive-", ".txt")
    try {
      val writer = new PrintWriter(tempFile)
      try {
        lines.foreach(writer.println)
      } finally {
        writer.close()
      }

      val tui = new Tui(width, height, tempFile.getAbsolutePath)
      tui.print_Tui()

    } finally {
      tempFile.delete()
    }
  }

  private def readMultilineInput(): List[String] = {
    import scala.io.StdIn.readLine
    LazyList.continually(readLine()).takeWhile(line => line != null && line.nonEmpty).toList
  }
}