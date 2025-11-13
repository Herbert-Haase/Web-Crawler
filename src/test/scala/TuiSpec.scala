package de.htwg.webscraper.aview

import de.htwg.webscraper.controller.Controller
import de.htwg.webscraper.model.Data
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class TuiSpec extends AnyWordSpec with Matchers {

  "A TUI" should {

    "build an empty TUI string when data is empty" in {
      val controller = new Controller()
      val tui = new Tui(controller, 10, 3) // width=10, height=3
      controller.data = Data(List.empty)

      val expectedString =
        "+----------+\n" +
        "|          |\n" +
        "|          |\n" +
        "|          |\n" +
        "+----------+"
      
      tui.buildTuiString() shouldBe expectedString
    }

    "build a TUI string with a single short line" in {
      val controller = new Controller()
      val tui = new Tui(controller, 10, 3) // width=10, height=3
      controller.data = Data(List("Hello"))

      val expectedString =
        "+----------+\n" +
        "|Hello     |\n" +
        "|          |\n" +
        "|          |\n" +
        "+----------+"
      
      tui.buildTuiString() shouldBe expectedString
    }

    "build a TUI string with multiple lines fewer than height" in {
      val controller = new Controller()
      val tui = new Tui(controller, 10, 3) // width=10, height=3
      controller.data = Data(List("Hello", "World"))

      val expectedString =
        "+----------+\n" +
        "|Hello     |\n" +
        "|World     |\n" +
        "|          |\n" +
        "+----------+"
      
      tui.buildTuiString() shouldBe expectedString
    }

    "build a TUI string with multiple lines exactly equal to height" in {
      val controller = new Controller()
      val tui = new Tui(controller, 10, 3) // width=10, height=3
      controller.data = Data(List("Line 1", "Line 2", "Line 3"))

      val expectedString =
        "+----------+\n" +
        "|Line 1    |\n" +
        "|Line 2    |\n" +
        "|Line 3    |\n" +
        "+----------+"
      
      tui.buildTuiString() shouldBe expectedString
    }

    "build a TUI string and truncate lines more than height" in {
      val controller = new Controller()
      val tui = new Tui(controller, 10, 3) // width=10, height=3
      controller.data = Data(List("Line 1", "Line 2", "Line 3", "Line 4"))

      val expectedString =
        "+----------+\n" +
        "|Line 1    |\n" +
        "|Line 2    |\n" +
        "|Line 3    |\n" +
        "+----------+"
      
      tui.buildTuiString() shouldBe expectedString
    }

    "build a TUI string with simple word wrapping" in {
      val controller = new Controller()
      val tui = new Tui(controller, 10, 5) // width=10, height=5
      controller.data = Data(List("Hello world this is a test"))

      // "Hello" (5)
      // "world" (5) + " " + "this" (4) = 10 -> "world this"
      // "is" (2) + " " + "a" (1) + " " + "test" (4) = 9 -> "is a test"
      val expectedString =
        "+----------+\n" +
        "|Hello     |\n" +
        "|world this|\n" +
        "|is a test |\n" +
        "|          |\n" +
        "|          |\n" +
        "+----------+"
      
      tui.buildTuiString() shouldBe expectedString
    }

    "build a TUI string by breaking words longer than width" in {
      val controller = new Controller()
      val tui = new Tui(controller, 10, 5) // width=10, height=5
      controller.data = Data(List("abcdefghijklmnopqrstuvwxyz"))

      // "abcdefghij"
      // "klmnopqrst"
      // "uvwxyz"
      val expectedString =
        "+----------+\n" +
        "|abcdefghij|\n" +
        "|klmnopqrst|\n" +
        "|uvwxyz    |\n" +
        "|          |\n" +
        "|          |\n" +
        "+----------+"
      
      tui.buildTuiString() shouldBe expectedString
    }

    "build a TUI string with mixed normal words and long words" in {
      val controller = new Controller()
      val tui = new Tui(controller, 10, 5) // width=10, height=5
      controller.data = Data(List("A verylongwordinthetext"))

      // "A"
      // "verylongwo"
      // "rdinthetex"
      // "t"
      val expectedString =
        "+----------+\n" +
        "|A         |\n" +
        "|verylongwo|\n" +
        "|rdinthetex|\n" +
        "|t         |\n" +
        "|          |\n" +
        "+----------+"
      
      tui.buildTuiString() shouldBe expectedString
    }

    "build a TUI string correctly handling multiple lines from controller" in {
      val controller = new Controller()
      val tui = new Tui(controller, 10, 5) // width=10, height=5
      controller.data = Data(List("First line wraps", "Second line"))

      // "First line"
      // "wraps"
      // "Second"
      // "line"
      val expectedString =
        "+----------+\n" +
        "|First line|\n" +
        "|wraps     |\n" +
        "|Second    |\n" +
        "|line      |\n" +
        "|          |\n" +
        "+----------+"
      
      tui.buildTuiString() shouldBe expectedString
    }

    "build a TUI string ignoring empty lines in the data" in {
      val controller = new Controller()
      val tui = new Tui(controller, 10, 5) // width=10, height=5
      controller.data = Data(List("Line 1", "", "Line 3"))

      // "Line 1"
      // "Line 3"
      val expectedString =
        "+----------+\n" +
        "|Line 1    |\n" +
        "|Line 3    |\n" +
        "|          |\n" +
        "|          |\n" +
        "|          |\n" +
        "+----------+"
      
      tui.buildTuiString() shouldBe expectedString
    }
  }
}