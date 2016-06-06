package com.scalaq.survey.model.answer

import scalaq.persistence

/**
  * All answer types must extend this Trait and impelemnt its methods.
  * If you want to make your own answer type
  * than you must extend this trait and implement its definitions
  *
  */
trait Answer {
  /**
    * Object that represents answer for question
    * @return
    */
  def answer: Object


  /**
    * returns persistance class of answer which is used for database management
    * @return
    */
  def getPersistenceAnswer(): persistence.Answer
}
