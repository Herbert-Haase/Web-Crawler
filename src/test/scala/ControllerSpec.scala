package de.htwg.webscraper.controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import java.io.{File, PrintWriter}

// Helper class for testing the Observable pattern
class TestObserver extends Observer {
  var wasNotified = false
  var isFilter = false
  override def update(isFilterUpdate: Boolean): Unit = {
    wasNotified = true
    isFilter = isFilterUpdate
  }
}

class ControllerSpec extends AnyWordSpec with Matchers {

  "A Controller" should {
    "process a file and notify observers" in {
      val controller = new Controller()
      val tempFile = File.createTempFile("test", ".txt")
      tempFile.deleteOnExit()
      val writer = new PrintWriter(tempFile)
      writer.println("Line 1 from file.")
      writer.println("Line 2 from file.")
      writer.close()

      controller.processFile(tempFile.getAbsolutePath)
      controller.data.wordCount should be(6)
    }

    "filter data by a word and recalculate stats" in {
      val controller = new Controller()
      controller.processText("First line with scala\nSecond line with java\nThird line with scala and python")
      controller.filterByWord("scala")

      controller.data.wordCount should be(10)
      controller.data.mostCommonWords should contain allOf (("scala", 2), ("with", 2), ("line", 2))
    }

    "handle a non-existent file gracefully to cover getOrElse" in {
      val controller = new Controller()
      val observer = new TestObserver
      controller.add(observer)
      val nonExistentPath = "/path/that/absolutely/does/not/exist.txt"

      controller.processFile(nonExistentPath)

      // Verify that the observer was notified of the change
      observer.wasNotified should be(true)

      // Verify that the data was updated with the error message
      controller.data.originalLines should have size 1
      controller.data.originalLines.head should be(s"Error: Could not read file '$nonExistentPath'.")
    }
  }
}