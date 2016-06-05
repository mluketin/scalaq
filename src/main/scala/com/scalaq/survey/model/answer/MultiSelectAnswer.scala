package com.scalaq.survey.model.answer

import java.util

import scalaq.persistence


/**
  * For multi select questions sequence contains index of marked answer
  * Example:
  * What do you watch?
  * a) Football
  * b) Basketball
  * c) Handball
  * d) Tennis
  *
  * if you watch football and handball you make multiple answer as follows:
  *
  * val answer = MutlipleAnswer(Seq(0, 2))
  *
  * 0 represents index of football, and 2 represents index of handball
  *
  * @param answer
  */
case class MultiSelectAnswer(answer: Option[Seq[Int]], other: Option[String] = None) extends Answer {

  def getPersistenceAnswer(): persistence.Answer = {
    val a = new persistence.Answer()

    val javaList: java.util.List[Integer] = new util.ArrayList[Integer]()
    if (answer != None) {
      for (ans <- answer.get) {
        javaList.add(ans.asInstanceOf[Integer])
      }
    }

    val spec = new persistence.MultiSelectAnswer()
      .setSelectedList(javaList)
      .setOther(if (other == None) "" else other.get)
    a.setSpec(spec)
    return a
  }
}

