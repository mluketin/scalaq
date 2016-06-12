package com.scalaq.survey.model.questionnaire

import com.scalaq.survey.ScalaToJavaConverter
import com.scalaq.survey.model.question.Question

import scalaq.persistence

/**
  *
  * @param name name for questionnaire
  * @param description optional description for questionnaire
  * @param questions sequence of questions that are in questionnaire
  */
case class Questionnaire(name: String, description: Option[String], questions: Seq[Question]) {

  /**
    * converts model questionnaire into persistence questionnaire.
    * (Note: this is not the same persistence object as you can find in database if you have already saved model Questionnaire)
    * @return
    */
  def getPersistanceQuestionnaire(): persistence.Questionnaire = {
    new persistence.Questionnaire()
      .setName(name)
      .setDescription(if (description == None) "" else description.get)
      .setQuestions(ScalaToJavaConverter.scalaToJavaQuestions(questions))
  }

}





