package com.scalaq.survey.model.questionnaire

import com.scalaq.survey.model.question.Question

case class Questionnaire(name: String, description: Option[String], questions: Seq[Question])





