package com.scalaq.survey.model.answer

import scalaq.persistence


case class SingleSelectAnswer(answer: Option[Integer], other: Option[String] = None) extends Answer {

  require(answer != None || other != None,
    "At least one answer must be provided, either index of offered answer from question, or something 'other'")

  def getPersistenceAnswer(): persistence.Answer = {
    val a = new persistence.Answer()
    val spec = new persistence.SingleSelectAnswer()
      .setSelected(if(answer == None) -1 else answer.get)
      .setOther(if(other == None) "" else other.get)
    a.setSpec(spec)
    return a
  }
}
