package de.htwg.webscraper.aview

import de.htwg.webscraper.controller.Controller
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class TuiSpec extends AnyWordSpec with Matchers {

  "A Tui's buildStatsString method" should {
    "create a correct statistics string" in {
      val controller = new Controller()
      val tui = new Tui(controller, 80, 10)
      controller.data = controller.data.copy(characterCount = 29, wordCount = 6, mostCommonWords = List(("three", 3), ("two", 2), ("one", 1)))

      val expected =
        """
          ||--- Statistics ---
          ||Total Characters: 29
          ||Total Words: 6
          ||Most Common Words: 'three' (3), 'two' (2), 'one' (1)
          ||--------------------
          |""".stripMargin
      tui.buildStatsString() should be(expected)
    }

    "show 'N/A' for most common words when there are no words" in {
      val controller = new Controller()
      val tui = new Tui(controller, 80, 10)
      controller.data = controller.data.copy(characterCount = 0, wordCount = 0, mostCommonWords = List.empty)

      val expected =
        """
          ||--- Statistics ---
          ||Total Characters: 0
          ||Total Words: 0
          ||Most Common Words: N/A
          ||--------------------
          |""".stripMargin
      tui.buildStatsString() should be(expected)
    }
  }

  "A Tui's buildTuiString method" should {
    "build a frame around simple text" in {
      val controller = new Controller()
      val tui = new Tui(controller, 20, 3)
      controller.data = controller.data.copy(displayLines = List("Hello", "World"))

      val expected =
        """
          |+--------------------+
          ||Hello               |
          ||World               |
          ||                    |
          |+--------------------+""".stripMargin.trim

      tui.buildTuiString().trim should be(expected)
    }
  }
  // This test should be added inside "A Tui's buildTuiString method" should { ... } block

  "break words that are longer than the specified width" in {
    val controller = new Controller()
    val tui = new Tui(controller, 10, 3) // Use a small width for testing
    controller.data = controller.data.copy(displayLines = List("A superlongword"))

    val expected =
      """
        |+----------+
        ||A         |
        ||superlongw|
        ||ord       |
        |+----------+""".stripMargin.trim

    tui.buildTuiString().trim should be(expected)
  }

  "wrap a line before processing a word that is too long" in {
      val controller = new Controller()
      val tui = new Tui(controller, 10, 3)
      controller.data = controller.data.copy(displayLines = List("short superlongword"))

      val expected =
      """
          |+----------+
          ||short     |
          ||superlongw|
          ||ord       |
          |+----------+""".stripMargin.trim

      tui.buildTuiString().trim should be(expected)
  }
  "wrap a line with multiple words to cover standard wrapping" in {
    val controller = new Controller()
    val tui = new Tui(controller, 15, 4) // A width of 15
    controller.data = controller.data.copy(displayLines = List("This line is just long enough to wrap."))

    // "This line is" has length 12. The next word "just" (4) makes the total 12 + 1 + 4 = 17, which is > 15.
    // This forces the wrap, testing the specific `else if` block.
    // "just long" is 9. The next word "enough" (6) makes it 9 + 1 + 6 = 16, which is > 15.
    // This forces a second wrap.
    val expected =
      """
        |+---------------+
        ||This line is   |
        ||just long      |
        ||enough to wrap.|
        ||               |
        |+---------------+""".stripMargin.trim

    tui.buildTuiString().trim should be(expected)
  }
}