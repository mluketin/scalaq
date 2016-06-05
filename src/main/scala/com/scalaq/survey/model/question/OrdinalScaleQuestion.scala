package com.scalaq.survey.model.question

import scalaq.persistence
import java.util.Random

import com.scalaq.survey.model.answer.{Answer, OrdinalScaleAnswer}

//how much are u satisfied with service?
// not satisfied at all [1 2 3 4 5] very satisfied
case class OrdinalScaleQuestion(questionText: String, questionDescription: Option[String], min: Int, max: Int, minLabel: Option[String], maxLabel: Option[String]) extends Question {
  def getRandomAnswer(): Answer = {
    val rand = new Random()
    val random_index = rand.nextInt(max - min) + min
    OrdinalScaleAnswer(random_index)
  }

  def getExportData(answers: Map[Question, Map[Answer, Int]]): Array[Array[Object]] = {
    val header: Array[Object] = Array("Answers:", "#")
    val map = answers.get(this).get
    val data = new Array[Array[Object]](max - min + 1)
    data(0) = header

    var br = 1
    for(i <- min until max) {
      data(br) = Array(i.asInstanceOf[Object], map.getOrElse(OrdinalScaleAnswer(i), 0).asInstanceOf[Object])
      br += 1
    }

//    for (entry <- map) {
//      data(br) = Array(entry._1.answer, entry._2.asInstanceOf[Object])
//      br += 1
//    }
    data
  }

  override def getPersistanceQuestion(): persistence.Question = {
    val q: persistence.Question = new persistence.Question()
    q.setBody(questionText)
    q.setDescription(if (questionDescription == None) "" else questionDescription.get)
    val ordinalScaleQuestionSpec = new persistence.OrdinalScaleQuestion()
      .setMin(min)
      .setMax(max)
      .setMinLabel(if (minLabel == None) "" else minLabel.get)
      .setMaxLabel(if (maxLabel == None) "" else maxLabel.get)
    q.setSpec(ordinalScaleQuestionSpec)
    return q
  }
}
