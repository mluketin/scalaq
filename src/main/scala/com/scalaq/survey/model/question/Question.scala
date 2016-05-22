package com.scalaq.survey.model.question

import com.scalaq.survey.model.answer.Answer

trait Question {

  def questionText: String

  def questionDescription: Option[String]

  def getExportData(questionMap: Map[Question, Map[Answer, Int]]): Array[Array[Object]]

  def getPersistanceQuestion(): scalaq.persistence.Question

  //for testing purpose
  def getRandomAnswer(): Answer
}











