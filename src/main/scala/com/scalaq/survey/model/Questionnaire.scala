package com.scalaq.survey.model

import com.scalaq.survey.model.answer.Answer
import com.scalaq.survey.model.question.{Question}

case class Questionnaire(name: String, description: Option[String], questions: Seq[Question])

case class CompletedQuestionnaire(questionnaire: Questionnaire, answers: Seq[Answer]) {
  require(questionnaire.questions.size == answers.size,
    "answers field must be the same size as question field in questionnaire")
}



