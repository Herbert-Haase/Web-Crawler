package de.htwg.webcrawler {
  object TUI
  def main(args: Array[String]): Unit = {
    val width = 3
    val height = 5

    // create window
    val horizontal_line = hline(width)

    println(horizontal_line)

    for (i <- 0 until height)
      println(line(width))

    println(horizontal_line)
}
  def hline(width: Int = 0): String = {
    "+" + "-" * width + "+"
  }

  def line(width: Int = 0, text: String = ""): String = {
        if (text.isEmpty) {
        "|" + " " * width + "|"
    } else {
        "|" + text.padTo(width, ' ').substring(0, width) + "|"
    }
  }

  def wrapFileContents(file: String, width: Int): List[String] = {
    import scala.io.Source
    import scala.util.Using
    import scala.collection.mutable.ListBuffer

    Using(Source.fromFile(file)) { source =>
      val wrappedLines = ListBuffer[String]()

      for (line <- source.getLines()) {
        val words = line.split("\\s+").filter(_.nonEmpty)
        var currentLine = new StringBuilder()

        for (word <- words) {
          if (word.length > width) {
            // Word is longer than the line width, so it must be split.

            if (currentLine.nonEmpty) {
              wrappedLines += currentLine.toString()
              currentLine.clear()
            }

            // Split the oversized word into chunks of 'width'.
            word.grouped(width).foreach { chunk =>
              wrappedLines += chunk
            }

            } else if (currentLine.nonEmpty && currentLine.length + 1 + word.length > width) {
              // Word doesn't fit on the current line, so start a new one.
              wrappedLines += currentLine.toString()
              currentLine.clear()
              currentLine.append(word)

            } else {
              // Word fits, so add it to the current line.
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

      }.getOrElse {
        println(s"Error reading file: $file")
        List.empty[String]
      }
  }

}

