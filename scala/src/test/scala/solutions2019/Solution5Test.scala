package com.franchini
package solutions2019

import org.scalatest.funsuite.AnyFunSuite

import java.io.{ByteArrayOutputStream, StringReader}
import scala.io.Source

/**
  *
  */
class Solution5Test extends AnyFunSuite {

  test("Part One") {
    val out = new ByteArrayOutputStream()
    Console
      .withOut(out) {
        Console.withIn(new StringReader("1")) {
          val source = Source.fromResource("2019/05-input.txt")
          Solution5.partOne(
            try source.mkString
            finally source.close()
          )
        }
      }
    assertResult(7157989)(out.toString.trim.toInt)
  }

  test("Part Two Sample") {
    val out = new ByteArrayOutputStream()
    Console
      .withOut(out) {
        Console.withIn(new StringReader("7")) {
          val source = Source.fromResource("2019/05-sample.txt")
          Solution5.partTwo(
            try source.mkString
            finally source.close()
          )
        }
      }
    assertResult(999)(out.toString.trim.toInt)
    out.reset()
    Console
      .withOut(out) {
        Console.withIn(new StringReader("8")) {
          val source = Source.fromResource("2019/05-sample.txt")
          Solution5.partTwo(
            try source.mkString
            finally source.close()
          )
        }
      }
    assertResult(1000)(out.toString.trim.toInt)
    out.reset()
    Console
      .withOut(out) {
        Console.withIn(new StringReader("9")) {
          val source = Source.fromResource("2019/05-sample.txt")
          Solution5.partTwo(
            try source.mkString
            finally source.close()
          )
        }
      }
    assertResult(1001)(out.toString.trim.toInt)
  }

  test("Part Two") {
    val out = new ByteArrayOutputStream()
    Console
      .withOut(out) {
        Console.withIn(new StringReader("5")) {
          val source = Source.fromResource("2019/05-input.txt")
          Solution5.partTwo(
            try source.mkString
            finally source.close()
          )
        }
      }
    assertResult(7873292)(out.toString.trim.toInt)
  }

}
