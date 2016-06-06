package com.scalaq.survey.model.answer

import scalaq.persistence

/**
  * This type of answer is used for OrdinalScaleQuestions
  *
  * See documentation of OrdinalScaleQuestion for example.
  *
  * @param answer
  */
case class OrdinalScaleAnswer(answer: Integer) extends Answer{

  def getPersistenceAnswer(): persistence.Answer = {
    val a = new persistence.Answer()
    val spec = new persistence.OrdinalScaleAnswer().setSelected(answer)
    a.setSpec(spec)
    return a
  }

}
