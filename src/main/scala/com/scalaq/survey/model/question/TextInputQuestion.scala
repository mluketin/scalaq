package com.scalaq.survey.model.question

import com.scalaq.survey.model.answer.{Answer, TextAnswer}

import scalaq.persistence
import scalaq.persistence.{QuestionSpec, TextInputQuestion}

//input field for text
case class TextInputQuestion(questionText: String, questionDescription: Option[String]) extends Question {
  def getRandomAnswer(): Answer = {
    TextAnswer("random answer")
  }

  def getExportData(answers: Map[Question, Map[Answer, Int]]): Array[Array[Object]] = {
    val header: Array[Object] = Array("Answers:", "#")
    val map = answers.get(this).get
    val data = new Array[Array[Object]](map.size + 1)
    data(0) = header

    var br = 1
    for (entry <- map) {
      data(br) = Array(entry._1.answer, entry._2.asInstanceOf[Object])
      br += 1
    }
    data
  }

  def getPersistanceQuestion(): scalaq.persistence.Question = {
    val q: scalaq.persistence.Question = new persistence.Question()
    q.setBody(questionText)
    q.setDescription(if (questionDescription == None) "" else questionDescription.get)
    q.setSpec(new persistence.TextInputQuestion())
    //TODO ovo gore odkomentiraj kad se testira
    return q
  }
}
