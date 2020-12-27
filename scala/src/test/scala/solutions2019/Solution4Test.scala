package com.franchini
package solutions2019

import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source

class Solution4Test extends AnyFunSuite {

  test("Part One") {
    val source = Source.fromResource("2019/04-input.txt")
    try assertResult(579)(
      Solution4.partOne(source.mkString)
    )
    finally source.close()
  }

  test("Part Two") {
    val source = Source.fromResource("2019/04-input.txt")
    try assertResult(358)(
      Solution4.partTwo(source.mkString)
    )
    finally source.close()
  }

}
