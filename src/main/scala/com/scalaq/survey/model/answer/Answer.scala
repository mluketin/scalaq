package com.scalaq.survey.model.answer

import scalaq.persistence

trait Answer {
  def answer: Object

  def getPersistenceAnswer(): persistence.Answer
}
