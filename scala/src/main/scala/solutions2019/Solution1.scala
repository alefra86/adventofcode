package com.franchini
package solutions2019

object Solution1 {

  def partOne(lines: Iterator[String]): Int = {
    lines.map(moduleMass => getFuel(Integer.parseInt(moduleMass))).sum
  }

  def partTwo(lines: Iterator[String]): Long = {
    var totalFuel = 0
    for (moduleMass <- lines) {
      var fuelPerMass = getFuel(Integer.parseInt(moduleMass))
      totalFuel += fuelPerMass
      while ({
        fuelPerMass = getFuel(fuelPerMass)
        fuelPerMass
      } > 0) {
        totalFuel += fuelPerMass
      }
    }
    totalFuel
  }

  private def getFuel(i: Int) = {
    Math.floor(i / 3).toInt - 2
  }
}
