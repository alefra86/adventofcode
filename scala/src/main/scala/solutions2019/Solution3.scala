package com.franchini
package solutions2019

object Solution3 {

  private final val DIRECTIONS = Map('L' -> (-1, 0), 'U' -> (0, 1), 'R' -> (1, 0), 'D' -> (0, -1))

  def partOne(lines: String): Int = {
    val (firstCmd, secondCmd) = getCommands(lines)
    (getPoints(firstCmd).keySet & getPoints(secondCmd).keySet).map(t => Math.abs(t._1) + Math.abs(t._2)).min
  }

  def partTwo(lines: String): Int = {
    val (firstCmd, secondCmd) = getCommands(lines)
    val firstPoints = getPoints(firstCmd)
    val secondPoints = getPoints(secondCmd)
    (firstPoints.keySet & secondPoints.keySet).map(t => firstPoints(t) + secondPoints(t)).min
  }

  private def getCommands(lines: String) = {
    val firstCmd :: secondCmd :: _ = for (line <- lines.split("\n").toList) yield line.split(',')
    (firstCmd, secondCmd)
  }

  private def getPoints(paths: Array[String]): Map[(Int, Int), Int] = {
    var points = Map[(Int, Int), Int]()
    var x = 0
    var y = 0
    var distance = 0
    for (path <- paths) {
      val direction = DIRECTIONS(path.charAt(0))
      for (_ <- 0 until Integer.parseInt(path.substring(1))) {
        x += direction._1
        y += direction._2
        distance += 1
        if (!points.contains((x, y))) {
          points = points + ((x, y) -> distance)
        }
      }
    }
    points
  }
}
