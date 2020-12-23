package com.franchini
package solutions2020

import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source

class Solution2Test extends AnyFunSuite {

  test("Part One Sample") {
    val bufferedSource = Source.fromResource("2020/02-sample.txt")
    assertResult(2)(Solution2.partOne(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part One") {
    val bufferedSource = Source.fromResource("2020/02-input.txt")
    assertResult(556)(Solution2.partOne(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part Two Sample") {
    val bufferedSource = Source.fromResource("2020/02-sample.txt")
    assertResult(1)(Solution2.partTwo(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part Two") {
    val bufferedSource = Source.fromResource("2020/02-input.txt")
    assertResult(605)(Solution2.partTwo(bufferedSource.getLines()))
    bufferedSource.close
  }

}
