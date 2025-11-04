package de.htwg.webscraper

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.webscraper
import java.io.{File, PrintWriter}

class WebscraperSpec extends AnyWordSpec with Matchers {
  // Helper function to create a temporary file with specific content for a test
  def withTempFile(content: String)(testCode: String => Any): Unit = {
    val file = File.createTempFile("test", ".txt")
    try {
      new PrintWriter(file) { write(content); close() }
      testCode(file.getAbsolutePath)
    } finally {
      file.delete()
    }
  }

  "A Tui class" should {

    "build a fully formatted window string using its build_all method" in {
      val fileContent = "hello\nworld"
      withTempFile(fileContent) { filePath =>
        val width = 10
        val height = 4
        val tui = new Tui(width, height, filePath)

        val expected =
          "+----------+\n" +
          "|hello     |\n" +
          "|world     |\n" +
          "|          |\n" +
          "|          |\n" +
          "+----------+"

        tui.build_all() should be(expected)
      }
    }

    "The wrapFileContents method" should {
      val tui = new Tui(1, 1, "")

      "wrap a line that has a short word before a very long word" in {
        val fileContent = "word superlongword"
        withTempFile(fileContent) { filePath =>
          val result = tui.wrapFileContents(filePath, 10)
          result should be(List("word", "superlongw", "ord"))
        }
      }

      "keep multiple words on the same line if they fit" in {
        val fileContent = "hello world test"
        withTempFile(fileContent) { filePath =>
          val result = tui.wrapFileContents(filePath, 20)
          result should be(List("hello world test"))
        }
      }

      "return an empty list if the file does not exist" in {
        val result = tui.wrapFileContents("non-existent-file.txt", 10)
        result should be(List.empty[String])
      }

      "wrap a line when a word does not fit" in {
        val fileContent = "hello world again"
        withTempFile(fileContent) { filePath =>
          val result = tui.wrapFileContents(filePath, 10)
          result should be(List("hello", "world", "again"))
        }
      }
    }
  }
  "The Input function(s)" should {
      val tui = new Tui(1, 1, "")
  "Give a greeting" in {
    val greeting = "Welcome to WebScraper"
    tui.giveGreeting().should(be(greeting))
  }
  "Ask for Type of input" in {
    val typeOfInputQuestion = "What type of input do you prefer? (stdin/file/url)"
    tui.askTypeOfInput().should(be(typeOfInputQuestion))
  }
  "Get Type of input" in {
    tui.getTypeOfInput().should(be oneOf("stdin","file","url"))
  }
  "Ask for input file or url" in { // Do not call if the user selected stdin
    val inputfile = "Which file or url should be used?"
    tui.askInputFile().should(be(inputfile))
  }
  "Get input file" in { // Do not call if the user selected stdin 
    tui.getTypeOfInput().shouldBe(a[String])
  }
  "Get stdin" in { // Do not call if the user selected file/url
    tui.getStdin().shouldBe(a[String])
  }
  "Write temporary file" in {
    val fileContent = "Hello World"
    tui.writeTemporaryFile(fileContent).shouldBe(a[File])
  }
}

}
