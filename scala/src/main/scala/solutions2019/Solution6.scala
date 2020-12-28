package com.franchini
package solutions2019

import scala.collection.mutable

object Solution6 {

  def partOne(lines: Iterator[String]): Int = {
    val connections = mutable.Map[String, mutable.Set[String]]()
    for (line <- lines) {
      val left :: right :: _ = line.split("\\)").toList
      connections.getOrElseUpdate(left, mutable.Set()).add(right)
    }
    getOrbits("COM", connections)._1
  }

  private def getOrbits(
      current: String,
      connections: mutable.Map[String, mutable.Set[String]]
  ): (Int, Int) = {
    var orbits = 0
    var following = 0
    if (connections.contains(current)) {
      for (right <- connections(current)) {
        val result = getOrbits(right, connections)
        orbits += result._1 + result._2 + 1
        following += result._2 + 1
      }
    }
    (orbits, following)
  }

  def partTwo(lines: Iterator[String]): Int = {
    val connections = mutable.Map[String, mutable.Set[String]]()
    for (line <- lines) {
      val left :: right :: _ = line.split("\\)").toList
      connections.getOrElseUpdate(left, mutable.Set()).add(right)
      connections.getOrElseUpdate(right, mutable.Set()).add(left)
    }
    val objectSeen = mutable.Map[String, Int]()
    assert(connections("YOU").size == 1)
    val objectToVisit = mutable.Queue[(String, Int)]((connections("YOU").head, 0))
    while (objectToVisit.nonEmpty && objectToVisit.head._1 != "SAN") {
      val obj = objectToVisit.dequeue
      objectSeen += obj
      if (connections.contains(obj._1)) {
        objectToVisit.enqueueAll(for (o <- connections(obj._1) if !objectSeen.keySet.contains(o)) yield (o, obj._2 + 1))
      }
    }
    objectToVisit.dequeue._2 - 1
  }

}
