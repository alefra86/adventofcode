package com.franchini
package solutions2019

object Solution2 {

  private val modulus = 19690720

  def partOneSample(line: String): Int = {
    compute(getInitialProgram(line))(0)
  }

  def partOne(line: String): Int = {
    compute(getInitialProgram(line), 12, 2)(0)
  }

  def partTwo(line: String): Long = {
    val initialProgram: Array[Int] = getInitialProgram(line)
    (for (noun <- LazyList range (0, 100); verb <- LazyList range (0, 100))
      yield compute(initialProgram.clone(), noun, verb)(0) == modulus).takeWhile(_ == false).size
  }

  private def getInitialProgram(line: String) = {
    line.replaceAll("\n", "").split(",").map(i => Integer.parseInt(i))
  }

  private def compute(programs: Array[Int], noun: Int = -1, verb: Int = -1) = {
    if (noun != -1) programs(1) = noun
    if (verb != -1) programs(2) = verb
    (for (pointer <- LazyList from programs.indices.by(4)) yield {
      val (first, second) = (programs(programs(pointer + 1)), programs(programs(pointer + 2)))
      programs(programs(pointer + 3)) = programs(pointer) match {
        case 1 => first + second
        case 2 => first * second
      }
      pointer
    }).takeWhile(p => programs(p + 4) != 99).force
    programs
  }
}
