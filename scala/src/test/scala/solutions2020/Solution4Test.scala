package com.franchini
package solutions2020

import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source

class Solution4Test extends AnyFunSuite {

  test("Part One Sample") {
    val bufferedSource = Source.fromResource("2020/04-sample.txt")
    assertResult(2)(Solution4.partOne(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part One") {
    val bufferedSource = Source.fromResource("2020/04-input.txt")
    assertResult(256)(Solution4.partOne(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part Two Sample") {
    val bufferedSource = Source.fromResource("2020/04-sample.txt")
    assertResult(2)(Solution4.partTwo(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part Two") {
    val bufferedSource = Source.fromResource("2020/04-input.txt")
    assertResult(198)(Solution4.partTwo(bufferedSource.getLines()))
    bufferedSource.close
  }

}
