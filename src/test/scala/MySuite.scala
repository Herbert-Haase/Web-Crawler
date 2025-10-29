package de.htwg.webcrawler

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.webcrawler.TUI.* // Import your functions

class WebCrawlerSpec extends AnyWordSpec with Matchers {

  "A TUI" should {
    "have a horizontal line of form '++'" in {
      hline() should be("++")
    }
    "have a scalable horizontal line" in {
      hline(3) should be("+---+")
    }
    "have an empty line with default width" in {
      line() should be("|" + "|")
    }
    "have a line with specific text and width" in {
      line(0, "hello") should be("||")
      line(6, "") should be("|      |")
      line(2, "hello") should be("|he|")
      line(9, "hello") should be("|hello    |")
    }
    "have wrap lines" in {
    val resource = getClass.getClassLoader.getResource("example.txt")
    val filePath = resource.getPath

    val expectedOutput = List(
      "hello",
      "world",
      "hi",
      "hi",
      "averyl",
      "ongwor",
      "dthatm",
      "ustbes",
      "plit"
    )
    wrapFileContents(filePath, 6) should be(expectedOutput)
    }
  }
}
