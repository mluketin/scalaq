package com.scalaq.survey.model.questionnaire

import com.scalaq.survey.converter.ScalaToJavaConverter
import com.scalaq.survey.database.DbAdapter
import com.scalaq.survey.model.answer.Answer

import scalaq.persistence

/**
  *
  * @param questionnaire questionnaire that is completed
  * @param answers sequence of answers for questionnaire in first parameter. Size of sequence must be the same as size of questions in questionnaire. For unanswered questions there is Unanswered type of Answer.
  */
case class CompletedQuestionnaire(questionnaire: Questionnaire, answers: Seq[Answer]) {
  require(questionnaire.questions.size == answers.size,
    "answers field must be the same size as question field in questionnaire")

  def getPersistenceCompleteQuestionnaire(): persistence.CompletedQuestionnaire = {
    new persistence.CompletedQuestionnaire()
      .setQuestionnaire(DbAdapter.getPersistanceQuestionnaire(questionnaire))
      .setAnswers(ScalaToJavaConverter.scalaToJavaAnswers(answers))
  }
}