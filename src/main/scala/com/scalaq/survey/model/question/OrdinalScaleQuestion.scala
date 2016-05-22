package com.scalaq.survey.model.question

import com.scalaq.survey.model._

import scalaq.persistence
import java.util.Random

import com.scalaq.survey.model.answer.{Answer, SingleAnswer}


//how much are u satisfied with service?
// not satisfied at all [1 2 3 4 5] very satisfied
case class OrdinalScaleQuestion(questionText: String, questionDescription: Option[String], min: Int, max: Int, minLabel: Option[String], maxLabel: Option[String]) extends Question {
  def getRandomAnswer(): Answer = {
    val rand = new Random()
    val random_index = rand.nextInt(max - min) + min
    SingleAnswer(random_index)
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

  override def getPersistanceQuestion(): persistence.Question = {
    val q: scalaq.persistence.Question = new persistence.Question()
    q.setBody(questionText)
    q.setDescription(if (questionDescription == None) "" else questionDescription.get)
    val multiSelectQuestionSpec = new persistence.OrdinalScaleQuestion()
      .setMin(min)
      .setMax(max)
      .setMinLabel(if (minLabel == None) "" else minLabel.get)
      .setMaxLabel(if (maxLabel == None) "" else maxLabel.get)

    q.setSpec(multiSelectQuestionSpec)
    //TODO ovo gore odkomentiraj kad se testira
    return q
  }
}
