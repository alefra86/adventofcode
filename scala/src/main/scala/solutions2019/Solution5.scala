package com.franchini
package solutions2019

import scala.io.StdIn

object Solution5 {

  val Debug = false

  def partOne(line: String): Unit = {
    val program = getInitialProgram(line)
    var pointer = 0
    val interpreter: Seq[Operation] = Seq(new Add, new Mul, new Input, new Output)
    while (program(pointer) != 99) {
      val instruction = program(pointer)
      pointer = interpreter.find(_.canExecute(instruction % 100)).get.execute(instruction, program, pointer)
    }
  }

  def partTwo(line: String): Unit = {
    val program = getInitialProgram(line)
    var pointer = 0
    val interpreter: Seq[Operation] = Seq(new Add, new Mul, new Input, new Output, new Jit, new Jif, new LessThan, new Equals)
    while (program(pointer) != 99) {
      val instruction = program(pointer)
      pointer = interpreter.find(_.canExecute(instruction % 100)).get.execute(instruction, program, pointer)
    }
  }

  trait Operation {

    def canExecute(opCode: Int): Boolean

    def execute(instruction: Int, program: Array[Int], pointer: Int): Int

    def getFirstParameter(instruction: Int, pointer: Int, program: Array[Int]): Int = {
      program(ParameterModes.getByMode(instruction / 100 % 10).getPointer(pointer, program))
    }

    def getSecondParameter(instruction: Int, pointer: Int, program: Array[Int]): Int = {
      program(ParameterModes.getByMode(instruction / 1000).getPointer(pointer, program))
    }

  }

  trait ArithmeticOperation extends Operation {

    override def execute(instruction: Int, program: Array[Int], pointer: Int): Int = {
      program(program(pointer + 3)) = calculate(
        getFirstParameter(instruction, pointer + 1, program),
        getSecondParameter(instruction, pointer + 2, program)
      )
      pointer + 4
    }

    def calculate(first: Int, second: Int): Int

  }

  class Add extends ArithmeticOperation {
    override def calculate(first: Int, second: Int): Int = first + second

    override def canExecute(opCode: Int): Boolean = opCode == 1
  }

  class Mul extends ArithmeticOperation {
    override def calculate(first: Int, second: Int): Int = first * second

    override def canExecute(opCode: Int): Boolean = opCode == 2
  }

  trait StdIO extends Operation {}

  class Input extends StdIO {
    override def execute(instruction: Int, program: Array[Int], pointer: Int): Int = {
      program(program(pointer + 1)) = StdIn.readInt()
      pointer + 2
    }

    override def canExecute(opCode: Int): Boolean = opCode == 3
  }

  class Output extends StdIO {
    override def execute(instruction: Int, program: Array[Int], pointer: Int): Int = {
      val result = getFirstParameter(instruction, pointer + 1, program)
      if (result != 0 || Debug) {
        println(result)
      }
      pointer + 2
    }

    override def canExecute(opCode: Int): Boolean = opCode == 4
  }

  class Jit extends Operation {
    override def execute(instruction: Int, program: Array[Int], pointer: Int): Int = {
      val result = getFirstParameter(instruction, pointer + 1, program)
      if (result != 0) {
        getSecondParameter(instruction, pointer + 2, program)
      } else {
        pointer + 3
      }
    }

    override def canExecute(opCode: Int): Boolean = opCode == 5
  }

  class Jif extends Operation {
    override def execute(instruction: Int, program: Array[Int], pointer: Int): Int = {
      val result = getFirstParameter(instruction, pointer + 1, program)
      if (result == 0) {
        getSecondParameter(instruction, pointer + 2, program)
      } else {
        pointer + 3
      }
    }

    override def canExecute(opCode: Int): Boolean = opCode == 6
  }

  class LessThan extends Operation {
    override def execute(instruction: Int, program: Array[Int], pointer: Int): Int = {
      var result = 0
      if (getFirstParameter(instruction, pointer + 1, program) < getSecondParameter(instruction, pointer + 2, program)) {
        result = 1
      }
      program(program(pointer + 3)) = result
      pointer + 4
    }

    override def canExecute(opCode: Int): Boolean = opCode == 7
  }

  class Equals extends Operation {

    override def execute(instruction: Int, program: Array[Int], pointer: Int): Int = {
      var result = 0
      if (getFirstParameter(instruction, pointer + 1, program) == getSecondParameter(instruction, pointer + 2, program)) {
        result = 1
      }
      program(program(pointer + 3)) = result
      pointer + 4
    }

    override def canExecute(opCode: Int): Boolean = opCode == 8
  }

  object ParameterModes extends Enumeration {

    protected case class ParameterModes(mode: Int, private val pointerExtractor: (Int, Array[Int]) => Int) extends super.Val {
      def getPointer(pointer: Int, program: Array[Int]): Int = pointerExtractor.apply(pointer, program)
    }

    val Position: Value = ParameterModes(0, (pointer: Int, program: Array[Int]) => program(pointer))
    val Immediate: Value = ParameterModes(1, (pointer: Int, _: Array[Int]) => pointer)

    implicit def valueToParameterModes(x: Value): ParameterModes = x.asInstanceOf[ParameterModes]

    def getByMode(mode: Int): ParameterModes = {
      this.values.find(_.mode == mode).get
    }
  }

  private def getInitialProgram(line: String): Array[Int] = {
    line.replaceAll("\n", "").split(",").map(i => Integer.parseInt(i))
  }

  def main(args: Array[String]): Unit = {
    partTwo("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9")
  }

}
