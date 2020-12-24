package com.franchini
package solutions2020

import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source

class Solution3Test extends AnyFunSuite {


  test("Part One Sample") {
    val source = Source.fromResource("2020/03-sample.txt")
    val input = try source.mkString finally source.close()
    assertResult(7)(Solution3.partOne(input))
  }

  test("Part One") {
    val source = Source.fromResource("2020/03-input.txt")
    val input = try source.mkString finally source.close()
    assertResult(257)(Solution3.partOne(input))
  }

  test("Part Two Sample") {
    val source = Source.fromResource("2020/03-sample.txt")
    val input = try source.mkString finally source.close()
    assertResult(336)(Solution3.partTwo(input))
  }

  test("Part Two") {
    val source = Source.fromResource("2020/03-input.txt")
    val input = try source.mkString finally source.close()
    assertResult(1744787392L)(Solution3.partTwo(input))
  }

}
