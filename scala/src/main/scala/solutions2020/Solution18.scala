package com.franchini
package solutions2020

import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox

object Solution18 {

  def partOne(lines: Iterator[String]): Long = {
    val toolbox = currentMirror.mkToolBox()
    var ans = 0L
    for (line <- lines) {
      ans += toolbox
        .eval(
          toolbox.parse(
            "import com.franchini.solutions2020.Solution18.Num; " + line.replaceAll("\\*", "-").replaceAll("(\\d+)", "new Num($1)")
          )
        )
        .asInstanceOf[Num]
        .toLong
    }
    ans
  }

  def partTwo(lines: Iterator[String]): Long = {
    val toolbox = currentMirror.mkToolBox()
    var ans = 0L
    for (line <- lines) {
      ans += toolbox
        .eval(
          toolbox.parse(
            "import com.franchini.solutions2020.Solution18.Num; " + line
              .replaceAll("\\*", "-")
              .replaceAll("\\+", "/")
              .replaceAll("(\\d+)", "new Num($1)")
          )
        )
        .asInstanceOf[Num]
        .toLong
    }
    ans
  }

  class Num(var num: Long) {

    def +(that: Num): Num = {
      new Num(num + that.num)
    }

    def -(that: Num): Num = {
      new Num(num * that.num)
    }

    def /(that: Num): Num = {
      new Num(num + that.num)
    }

    def toLong: Long = num

    override def toString: String = num.toString

  }

}
