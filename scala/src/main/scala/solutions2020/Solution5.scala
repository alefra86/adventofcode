package com.franchini
package solutions2020


/**
 *
 */
object Solution5 {

  def partOne(lines: Iterator[String]): Int = lines.map(line => Integer.parseInt(line.replaceAll("[FL]", "0").replaceAll("[BR]", "1"), 2)).max

  def partTwo(lines: Iterator[String]): Int = {
    val seats = lines.map(line => Integer.parseInt(line.replaceAll("[FL]", "0").replaceAll("[BR]", "1"), 2)).toList.sorted
    seats.zipWithIndex.find { case (seatId, idx) => seats(idx + 1) - seatId == 2 }.head._1 + 1
  }

}
