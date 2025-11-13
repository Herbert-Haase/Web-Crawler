package de.htwg.webscraper.controller

import de.htwg.webscraper.model.Data
import scala.io.Source
import scala.util.Using
import scala.collection.mutable.ListBuffer

class Controller extends Observable {
  var data: Data = Data(List.empty)

  def processText(text: String): Unit = {
    data = Data(text.split("\n").toList)
    notifyObservers()
  }

  def processFile(filePath: String): Unit = {
    val linesFromFile = Using(Source.fromFile(filePath)) { source =>
      source.getLines().toList
    }.getOrElse {
      println(s"Error reading file: $filePath")
      List(s"Error: Could not read file '$filePath'.")
    }
    data = Data(linesFromFile)
    notifyObservers()
  }
}
