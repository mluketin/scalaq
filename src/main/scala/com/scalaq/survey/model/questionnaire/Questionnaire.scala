package com.scalaq.survey.model.questionnaire

import com.scalaq.survey.model.question.Question

/**
  *
  * @param name name for questionnaire
  * @param description optional description for questionnaire
  * @param questions sequence of questions that are in questionnaire
  */
case class Questionnaire(name: String, description: Option[String], questions: Seq[Question])





