package com.scalaq.survey.model.answer

import scalaq.persistence

case class TextAnswer(answer: String) extends Answer {


  def getPersistenceAnswer(): persistence.Answer = {
    val a = new persistence.Answer()
    val spec = new persistence.TextAnswer()
    a.setSpec(spec)
    return a
  }
}

