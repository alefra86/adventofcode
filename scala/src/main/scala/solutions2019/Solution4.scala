package com.franchini
package solutions2019

import scala.collection.mutable

object Solution4 {

  def partOne(lines: String): Int = {
    getCorrectPasswordNumber(lines, v => v >= 2)
  }

  def partTwo(lines: String): Int = {
    getCorrectPasswordNumber(lines, v => v == 2)
  }

  private def isCorrect(password: Int, doubleCheckCondition: Int => Boolean): Boolean = {
    var correct = true
    val digitsCounter = mutable.Map[Char, Int]().withDefaultValue(0)
    var index = 1
    val stringPassword = password.toString
    digitsCounter(stringPassword(0)) = 1
    while (correct && index < stringPassword.length) {
      if (stringPassword(index - 1) > stringPassword(index)) {
        correct = false
      }
      digitsCounter(stringPassword(index)) += 1
      index += 1
    }
    correct && digitsCounter.values.count(doubleCheckCondition) >= 1
  }

  private def getCorrectPasswordNumber(lines: String, function: Int => Boolean): Int = {
    val Array(start, end) = lines.trim.split("-").map(_.toInt)
    var ans = 0
    for (password <- start to end) {
      if (isCorrect(password, function)) {
        ans += 1
      }
    }
    ans
  }
}
