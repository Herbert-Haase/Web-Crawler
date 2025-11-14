package de.htwg.webscraper.aview

import de.htwg.webscraper.controller.{Controller, Observer}
import scala.io.StdIn.readLine
import scala.collection.mutable.ListBuffer

class Tui(controller: Controller, width: Int, height: Int) extends Observer {
  controller.add(this)

  def run(): Unit = {
    println("Welcome to WebScraper")
    askForInitialInput()
  }

  def askForInitialInput(): Unit = {
    println("\nEnter 'file <path>' to load a file, or 'text' to enter multiline text:")
    val input = readLine()
    input.split(" ").toList match {
      case "file" :: path :: Nil => controller.processFile(path)
      case "text" :: Nil         =>
        println("Please enter your text. Press Enter on an empty line to finish.")
        val lines = LazyList.continually(readLine()).takeWhile(line => line != null && line.nonEmpty).toList
        controller.processText(lines.mkString("\n"))
      case "exit" :: Nil => println("Goodbye!")
      case _ =>
        println("Invalid command.")
        askForInitialInput()
    }
  }

  override def update(isFilterUpdate: Boolean): Unit = {
    println(buildTuiString())

    if (isFilterUpdate) {
      println(s"--- Content filtered. Found ${controller.data.displayLines.size} matching lines. ---")
    }

    // Always show stats after displaying content
    println(buildStatsString())
    askForFilterOrNext()
  }

  private def askForFilterOrNext(): Unit = {
    println("Enter a word to filter the text, or type 'new' for new input, 'exit' to quit:")
    val input = readLine()
    input.toLowerCase match {
      case "new" => askForInitialInput()
      case "exit" => println("Goodbye!")
      case "" => askForFilterOrNext() // Ignore empty input
      case word => controller.filterByWord(word)
    }
  }

  def buildTuiString(): String = {
    val wrappedLines = wrapLines(controller.data.displayLines, width)
    val tower = buildTower(width, height, wrappedLines)
    val bar = buildBar(width)
    s"\n$bar\n$tower\n$bar"
  }

  def buildStatsString(): String = {
      val stats = controller.data
      val commonWords = stats.mostCommonWords.map { case (word, count) => s"'$word' ($count)" }.mkString(", ")
      s"""
      ||--- Statistics ---
      ||Total Characters: ${stats.characterCount}
      ||Total Words: ${stats.wordCount}
      ||Most Common Words: ${if (commonWords.isEmpty) "N/A" else commonWords}
      ||--------------------
      |""".stripMargin // <--- Move the |""" up to this line
    }

  private def buildBar(width: Int): String = "+" + "-" * width + "+"

  private def buildLine(width: Int, text: String): String = {
    "|" + text.padTo(width, ' ').substring(0, width) + "|"
  }

  private def buildTower(width: Int, height: Int, lines: List[String]): String = {
    (0 until height).map { i =>
      val text = if (i < lines.length) lines(i) else ""
      buildLine(width, text)
    }.mkString("\n")
  }

  private def wrapLines(lines: List[String], width: Int): List[String] = {
    val wrappedLines = ListBuffer[String]()
    for (line <- lines) {
      val words = line.split("\\s+").filter(_.nonEmpty)
      var currentLine = new StringBuilder()

      for (word <- words) {
        if (word.length > width) {
          if (currentLine.nonEmpty) {
            wrappedLines += currentLine.toString()
            currentLine.clear()
          }
          word.grouped(width).foreach(chunk => wrappedLines += chunk)
        } else if (currentLine.nonEmpty && currentLine.length + 1 + word.length > width) {
          wrappedLines += currentLine.toString()
          currentLine.clear()
          currentLine.append(word)
        } else {
          if (currentLine.nonEmpty) {
            currentLine.append(" ")
          }
          currentLine.append(word)
        }
      }
      if (currentLine.nonEmpty) {
        wrappedLines += currentLine.toString()
      }
    }
    wrappedLines.toList
  }
}