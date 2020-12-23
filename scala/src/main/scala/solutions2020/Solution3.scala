package com.franchini
package solutions2020

/**
 *
 */
object Solution3 {

  def partOne(input: String): Int = {
    slope(input.split("\n").map(s => s.toCharArray), 3)
  }

  def slope(lines: Array[Array[Char]], deltaY: Int, deltaX: Int = 1): Int = {
    var y = 0
    var x = 0
    var counter = 0
    while (x < lines.indices.size) {
      if (lines(x)(y) == '#')
        counter += 1
      x += deltaX
      y = (y + deltaY) % lines(0).length
    }
    counter
  }

  def partTwo(input: String): Int = {
    val grid = input.split("\n").map(s => s.toCharArray)
    var result = 1
    for ((x, y) <- Seq((1, 1), (3, 1), (5, 1), (7, 1), (1, 2)))
      result *= slope(grid, x, y)
    result
  }
}
