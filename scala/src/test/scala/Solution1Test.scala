package com.franchini

import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source

class Solution1Test extends AnyFunSuite {

  test("Part One Sample") {
    val bufferedSource = Source.fromResource("input1-sample.txt")
    assertResult(514579)(Solution1.partOne(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part One") {
    val bufferedSource = Source.fromResource("input1.txt")
    assertResult(138379)(Solution1.partOne(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part Two Sample") {
    val bufferedSource = Source.fromResource("input1-sample.txt")
    assertResult(241861950)(Solution1.partTwo(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part Two") {
    val bufferedSource = Source.fromResource("input1.txt")
    assertResult(85491920)(Solution1.partTwo(bufferedSource.getLines()))
    bufferedSource.close
  }

}