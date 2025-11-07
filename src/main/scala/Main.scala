package de.htwg.webscraper

@main def run(args: String*): Unit = {
  if (args.length < 2) {
    println("No arguments provided. Starting interactive mode.")
    val interactiveTui = new InteractiveTui(80,10)
    interactiveTui.run()
  } else {
    val x: Int = args(0).toInt
    val y: Int = args(1).toInt
    val file = "src/main/scala/TUI.scala" // Example file

    if (x < 1 || y < 1) {
      println("Error: Width and height must be positive numbers.")
    } else {
      val myTui: Tui = new Tui(x, y, file)
      myTui.print_Tui()
    }
  }
}