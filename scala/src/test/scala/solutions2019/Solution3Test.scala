package com.franchini
package solutions2019

import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source

class Solution3Test extends AnyFunSuite {

  test("Part One Sample") {
    val source = Source.fromResource("2019/03-sample.txt")
    try assertResult(159)(
      Solution3.partOne(source.mkString)
    )
    finally source.close()
  }

  test("Part One") {
    val source = Source.fromResource("2019/03-input.txt")
    try assertResult(2129)(
      Solution3.partOne(source.mkString)
    )
    finally source.close()
  }

  test("Part Two Sample") {
    val source = Source.fromResource("2019/03-sample.txt")
    try assertResult(610)(
      Solution3.partTwo(source.mkString)
    )
    finally source.close()
  }

  test("Part Two") {
    val source = Source.fromResource("2019/03-input.txt")
    try assertResult(134662)(
      Solution3.partTwo(source.mkString)
    )
    finally source.close()
  }

}
