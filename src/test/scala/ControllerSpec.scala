package de.htwg.webscraper.controller

import de.htwg.webscraper.model.Data
import de.htwg.webscraper.model.Observer
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import java.io.{File, PrintWriter}

class ControllerSpec extends AnyWordSpec with Matchers {

  "A Controller" should {
    var updated = false
    val testObserver = new Observer {
      override def update(): Unit = updated = true
    }

    "process text input" in {
      val controller = new Controller()
      controller.add(testObserver)
      updated = false // reset flag

      val testText = "Hello world\nThis is a test"
      controller.processText(testText)

      controller.data should be(Data(List("Hello world", "This is a test")))
      updated should be(true)
    }

    "process a file input" in {
      val controller = new Controller()
      controller.add(testObserver)
      updated = false // reset flag

      // Create a temporary file for testing
      val testFile = new File("testfile.txt")
      val writer = new PrintWriter(testFile)
      writer.println("Line 1 from file")
      writer.println("Line 2 from file")
      writer.close()

      controller.processFile(testFile.getPath)

      controller.data should be(Data(List("Line 1 from file", "Line 2 from file")))
      updated should be(true)

      // Clean up the temporary file
      testFile.delete()
    }

    "handle a non-existent file" in {
      val controller = new Controller()
      controller.add(testObserver)
      updated = false // reset flag

      val badFilePath = "nonexistentfile.txt"
      controller.processFile(badFilePath)

      controller.data.lines.head should include(s"Error: Could not read file '$badFilePath'")
      updated should be(true)
    }

    "start with empty data" in {
      val controller = new Controller()
      controller.data should be(Data(List.empty))
    }
  }
}