package de.htwg.webscraper

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.webscraper.TUI.* // Import your functions

class WebscraperSpec extends AnyWordSpec with Matchers {

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
  "The Input function(s)" should {
  "Give a greeting" in {
    val greeting = "Welcome to WebScraper"
    giveGreeting().should(be(greeting))
  }
  "Ask for Type of input" in {
    val typeOfInputQuestion = "What type of input do you prefer? (stdin/file/url)"
    askTypeOfInput().should(be(typeOfInputQuestion))
  }
  "Get Type of input" in {
    getTypeOfInput().should(be oneOf("stdin","file","url"))
  }
  "Ask for input file or url" in { // Do not call if the user selected stdin
    val inputfile = "Which file or url should be used?"
    askInputFile().should(be(inputfile))
  }
  "Get input file" in { // Do not call if the user selected stdin 
    getTypeOfInput().shouldBe(a[String])
  }
  "Get stdin" in { // Do not call if the user selected file/url
    getStdin().shouldBe(a[String])
  }
  "Write temporary file" in {
    val fileContent = "Hello World"
    writeTemporaryFile(fileContent).shouldBe(a[File])
  }
}

}
