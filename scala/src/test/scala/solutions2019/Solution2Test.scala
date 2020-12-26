package com.franchini
package solutions2019

import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source

/**
 *
 */
class Solution2Test extends AnyFunSuite {

  test("Part One Sample") {
    val source = Source.fromResource("2019/02-sample.txt")
    assertResult(3500)(Solution2.partOneSample(try source.mkString finally source.close()))
  }

  test("Part One") {
    val source = Source.fromResource("2019/02-input.txt")
    assertResult(7594646)(Solution2.partOne(try source.mkString finally source.close()))
  }

  test("Part Two") {
    val source = Source.fromResource("2019/02-input.txt")
    assertResult(3376)(Solution2.partTwo(try source.mkString finally source.close()))
  }

}
