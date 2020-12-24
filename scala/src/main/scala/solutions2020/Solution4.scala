package com.franchini
package solutions2020

import scala.util.matching.Regex


/**
 *
 */
object Solution4 {

  implicit def bool2int(b: Boolean): Int = if (b) 1 else 0

  val Fields = Set("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
  val ValidEyes = List("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
  val FieldPattern: Regex = "([a-z]*?):([^ ]*)".r

  val ByrValidator: String => Boolean = (year: String) => 1920 to 2002 contains year.toInt
  val IyrValidator: String => Boolean = (year: String) => 2010 to 2020 contains year.toInt
  val EyrValidator: String => Boolean = (year: String) => 2020 to 2030 contains year.toInt
  val HgtValidator: String => Boolean = (height: String) => {
    val ht :: _ = "\\d+".r.findAllIn(height).toList
    height match {
      case cm if cm.contains("cm") => 150 to 193 contains ht.toInt
      case in if in.contains("in") => 59 to 76 contains ht.toInt
      case _ => false
    }
  }
  val HclValidator: String => Boolean = (hair: String) => "^#[0-9a-f]{6}$".r.matches(hair)
  val EclValidator: String => Boolean = (eye: String) => ValidEyes.contains(eye)
  val PidValidator: String => Boolean = (pid: String) => "^[0-9]{9}$".r.matches(pid)

  val fieldValidators: Map[String, String => Boolean] = Map("byr" -> ByrValidator, "iyr" -> IyrValidator, "eyr" -> EyrValidator, "hgt" -> HgtValidator, "hcl" -> HclValidator, "ecl" -> EclValidator, "pid" -> PidValidator, "cid" -> (_ => true))

  def partOne(lines: Iterator[String]): Int = {
    getValidPassports(lines, _ => true)
  }

  def partTwo(lines: Iterator[String]): Int = {
    getValidPassports(lines, field => fieldValidators(field.group(1)).apply(field.group(2)))
  }

  def getValidPassports(lines: Iterator[String], fieldValidator: Regex.Match => Boolean): Int = {
    var passport = Set[String]()
    var counter = 0
    for (line <- lines) {
      if (line.isEmpty) {
        if (passport.size > Fields.size || passport.equals(Fields)) {
          counter += 1
        }
        passport = Set()
      } else {
        passport = passport ++ (for (field <- FieldPattern.findAllMatchIn(line) if fieldValidator.apply(field)) yield field.group(1)).toSet
      }
    }
    counter
  }
}
