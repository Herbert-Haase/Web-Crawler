package de.htwg.webscraper

import de.htwg.webscraper.controller.Controller
import de.htwg.webscraper.aview.Tui

@main def run(args: String*): Unit = {
  val controller = new Controller()
  val tui = if (args.length == 2) {
    new Tui(controller, args(0).toInt, args(1).toInt)
  } else {
    new Tui(controller, 80, 10)
  }
  tui.run()
}