package com.franchini
package solutions2020

object Solution2 {

  def partOne(lines: Iterator[String]): Int = {
    var counter = 0
    for (line <- lines) {
      val words: Array[String] = line.trim().split(" ")
      val low :: high :: _ = for (x <- words(0).split("-").toList) yield x.toInt
      val ch: Char = words(1)(0)
      val password = words(2)
      if ((low to high).contains(password.chars().filter(_ == ch).count())) {
        counter += 1
      }
    }
    counter
  }

  def partTwo(lines: Iterator[String]): Long = {
    var counter = 0
    for (line <- lines) {
      val words: Array[String] = line.trim().split(" ")
      val low :: high :: _ = for (x <- words(0).split("-").toList) yield x.toInt
      val ch: Char = words(1)(0)
      val password = words(2)
      if (password(low - 1) == ch ^ password(high - 1) == ch) {
        counter += 1
      }
    }
    counter
  }
}

