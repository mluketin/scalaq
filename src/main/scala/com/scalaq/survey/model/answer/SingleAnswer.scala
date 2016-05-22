package com.scalaq.survey.model.answer

import scalaq.persistence

/**
  * Created by Marin on 22.5.2016..
  */
//answer to single select questions (or Text input question, and ordinal scale question)
case class SingleAnswer(answer: Integer) extends Answer {

  def getPersistenceAnswer(): persistence.Answer = {
    val a = new persistence.Answer()
    val spec = new persistence.SingleAnswer().setSelected(answer)
    a.setSpec(spec)
    return a
  }
}
