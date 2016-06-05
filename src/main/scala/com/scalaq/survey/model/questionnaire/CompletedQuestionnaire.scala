package com.scalaq.survey.model.questionnaire

import com.scalaq.survey.model.answer.Answer

case class CompletedQuestionnaire(questionnaire: Questionnaire, answers: Seq[Answer]) {
  require(questionnaire.questions.size == answers.size,
    "answers field must be the same size as question field in questionnaire")
}
