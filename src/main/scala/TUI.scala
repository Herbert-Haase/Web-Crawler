package de.htwg.webscraper {
class Tui (x:Int, y:Int, file:String) {
  val pipe:String = "|"
  val plus:String = "+"
  val minus:String = "-"
  val space:String = " "

  def build_bar(x:Int): String = plus + minus * x + plus
  def build_line(width: Int = 0, text: String = ""): String = {
    if (text.isEmpty) pipe + " " * width + pipe else {
    pipe + text.padTo(width, ' ').substring(0, width) + pipe
    }}
  def build_tower(width: Int, height: Int, lines: List[String]): String = {
    (0 until height).map { i =>
    val text = if (i < lines.length) lines(i) else ""
    build_line(width, text)
    }.mkString("\n")
  }
  def build_all(): String = build_bar(x) + "\n" + build_tower(x, y, wrapFileContents(file, x)) + "\n" + build_bar(x)
  def print_Tui(): Unit = println(build_all())

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
}
