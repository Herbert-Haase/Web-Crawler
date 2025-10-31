package de.htwg.webscraper

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import java.io.{File, PrintWriter}
import de.htwg.webscraper.TUI.*

class WebCrawlerSpec extends AnyWordSpec with Matchers {

  "A TUI" should {

    "build a formatted window string" in {
      val lines = List("hello", "world")
      val width = 10
      val height = 4

      val expected =
        "+----------+\n" +
        "|hello     |\n" +
        "|world     |\n" +
        "|          |\n" +
        "|          |\n" +
        "+----------+"

      buildWindowString(height, width, lines) should be(expected)
    }
  }

  "The wrapFileContents function" should {
    def withTempFile(content: String)(testCode: String => Any): Unit = {
      val file = File.createTempFile("test", ".txt")
      try {
        new PrintWriter(file) { write(content); close() }
        testCode(file.getAbsolutePath)
      } finally {
        file.delete()
      }
    }

    "wrap a line that has a short word before a very long word" in {
      val fileContent = "word superlongword"
      withTempFile(fileContent) { filePath =>
        val result = wrapFileContents(filePath, 10)
        result should be(List("word", "superlongw", "ord"))
      }
    }

    "keep multiple words on the same line if they fit" in {
      val fileContent = "hello world test"
      withTempFile(fileContent) { filePath =>
        val result = wrapFileContents(filePath, 20)
        result should be(List("hello world test"))
      }
    }

    "return an empty list if the file does not exist" in {
      val result = wrapFileContents("non-existent-file.txt", 10)
      result should be(List.empty[String])
    }
    "wrap a line when a word does not fit" in {
      val fileContent = "hello world again"
      withTempFile(fileContent) { filePath =>
        val result = wrapFileContents(filePath, 10)
        result should be(List("hello", "world", "again"))
      }
    }
  }
}
