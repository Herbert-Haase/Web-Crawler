package de.htwg.webscraper

import de.htwg.webscraper.controller.Controller
import de.htwg.webscraper.view.Tui

@main def run(): Unit = {
  val controller = new Controller()
  val tui = new Tui(controller, 80, 10)
  tui.run()
}
