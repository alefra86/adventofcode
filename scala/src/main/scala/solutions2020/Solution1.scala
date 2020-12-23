package com.franchini
package solutions2020

object Solution1 {

  def partOne(lines: Iterator[String]): Int = {
    var numbers: Seq[Int] = Seq.empty
    val present = Array.fill(2020)(false)
    lines.foreach(line => {
      numbers = numbers :+ line.toInt
      present(line.toInt) = true
    })
    for (x <- numbers) {
      val position = 2020 - x
      if (position > 0 && present(position)) {
        return position * x
      }
    }
    0
  }

  def partTwo(lines: Iterator[String]): Long = {
    var numbers: Seq[Int] = Seq.empty
    val present = Array.fill(2020)(false)
    lines.foreach(lines => {
      numbers = numbers :+ lines.toInt
      present(lines.toInt) = true
    })
    for ((x, i) <- numbers.zipWithIndex) {
      for (y <- numbers.drop(i + 1)) {
        val position = 2020 - x - y
        if (position > 0 && present(position)) {
          return position * x * y
        }
      }
    }
    0
  }
}

