package de.htwg.webscraper.controller

import de.htwg.webscraper.model.Data
import scala.io.Source
import scala.util.Using

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
      List(s"Error: Could not read file '$filePath'.")
    }
    data = Data(linesFromFile)
    notifyObservers()
  }

  def filterByWord(word: String): Unit = {
    val filteredLines = data.originalLines.filter(_.toLowerCase.contains(word.toLowerCase))
    // Create a new Data object with recalculated stats based on the filtered lines
    data = Data.fromFiltered(data.originalLines, filteredLines)
    notifyObservers(isFilterUpdate = true)
  }
}