package com.franchini
package solutions2020

import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source

class Solution18Test extends AnyFunSuite {

  test("Part One Sample") {
    val bufferedSource = Source.fromResource("2020/18-sample.txt")
    assertResult(26 + 437 + 12240 + 13632)(Solution18.partOne(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part One") {
    val bufferedSource = Source.fromResource("2020/18-input.txt")
    assertResult(6923486965641L)(Solution18.partOne(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part Two Sample") {
    val bufferedSource = Source.fromResource("2020/18-sample.txt")
    assertResult(46 + 1445 + 669060 + 23340)(Solution18.partTwo(bufferedSource.getLines()))
    bufferedSource.close
  }

  test("Part Two") {
    val bufferedSource = Source.fromResource("2020/18-input.txt")
    assertResult(70722650566361L)(Solution18.partTwo(bufferedSource.getLines()))
    bufferedSource.close
  }

}
