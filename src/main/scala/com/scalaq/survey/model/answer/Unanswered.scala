package com.scalaq.survey.model.answer

import scalaq.persistence

case class Unanswered() extends Answer {

  def answer(): Object = None

  def getPersistenceAnswer(): persistence.Answer = {
    val a = new persistence.Answer()
    val spec = new persistence.Unanswered()
    a.setSpec(spec)
    return a
  }
}
