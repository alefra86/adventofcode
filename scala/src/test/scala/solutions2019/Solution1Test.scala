package com.franchini
package solutions2019

import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source

/**
 *
 */
class Solution1Test extends AnyFunSuite {

  test("Part One Sample") {
    val bufferedSource = Source.fromResource("2019/01-sample.txt")
    assertResult(658 + 33583)(Solution1.partOne(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part One") {
    val bufferedSource = Source.fromResource("2019/01-input.txt")
    assertResult(3337604)(Solution1.partOne(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part Two Sample") {
    val bufferedSource = Source.fromResource("2019/01-sample.txt")
    assertResult(50346 + 966 + 2 + 2)(Solution1.partTwo(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part Two") {
    val bufferedSource = Source.fromResource("2019/01-input.txt")
    assertResult(5003530)(Solution1.partTwo(bufferedSource.getLines()))
    bufferedSource.close
  }

}
