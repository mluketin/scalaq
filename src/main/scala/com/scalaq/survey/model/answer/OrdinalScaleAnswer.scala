package com.scalaq.survey.model.answer

import scalaq.persistence

case class OrdinalScaleAnswer(answer: Integer) extends Answer{

  def getPersistenceAnswer(): persistence.Answer = {
    val a = new persistence.Answer()
    val spec = new persistence.OrdinalScaleAnswer().setSelected(answer)
    a.setSpec(spec)
    return a
  }

}
