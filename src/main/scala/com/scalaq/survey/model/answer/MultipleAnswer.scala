package com.scalaq.survey.model.answer

import java.util

import scalaq.persistence


//answer for multiple select question and matrix question
case class MultipleAnswer(answer: Seq[Int]) extends Answer {

  def getPersistenceAnswer(): persistence.Answer = {
    val a = new persistence.Answer()

    val javaList: java.util.List[Integer] = new util.ArrayList[Integer]()
    for (ans <- answer) {
      javaList.add(ans.asInstanceOf[Integer])
    }

    val spec = new persistence.MultipleAnswers().setSelectedList(javaList)
    a.setSpec(spec)
    return a
  }
}
