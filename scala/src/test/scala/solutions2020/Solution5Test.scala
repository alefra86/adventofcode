package com.franchini
package solutions2020

import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source

class Solution5Test extends AnyFunSuite {

  test("Part One Sample") {
    val bufferedSource = Source.fromResource("2020/05-sample.txt")
    assertResult(357)(Solution5.partOne(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part One") {
    val bufferedSource = Source.fromResource("2020/05-input.txt")
    assertResult(974)(Solution5.partOne(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part Two") {
    val bufferedSource = Source.fromResource("2020/05-input.txt")
    assertResult(646)(Solution5.partTwo(bufferedSource.getLines()))
    bufferedSource.close
  }

}
