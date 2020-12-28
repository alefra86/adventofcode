package com.franchini
package solutions2019

import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source

/**
  *
  */
class Solution6Test extends AnyFunSuite {

  test("Part One Sample") {
    val bufferedSource = Source.fromResource("2019/06-sample.txt")
    assertResult(42)(Solution6.partOne(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part One") {
    val bufferedSource = Source.fromResource("2019/06-input.txt")
    assertResult(223251)(Solution6.partOne(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part Two Sample") {
    val bufferedSource = Source.fromResource("2019/06-sample2.txt")
    assertResult(4)(Solution6.partTwo(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part Two") {
    val bufferedSource = Source.fromResource("2019/06-input.txt")
    assertResult(430)(Solution6.partTwo(bufferedSource.getLines()))
    bufferedSource.close
  }

}
