package com.scalaq.survey.model.question

import scalaq.persistence
import java.util.Random

import com.scalaq.survey.model.answer.{Answer, OrdinalScaleAnswer}


/**
  * Used for questions that have some kind of scale.
  *
  * Example:
  *
  *   "How much are you satisfied with your internet provider?"
  *
  *   and than user is offered answers from 0 to 10 and has to pick one
  *
  *   'not satisfied at all' 0 1 2 ... 9 10 'very satisfied'
  *
  * For example if user chose option 8 as answer, than it will be OrdinalScaleAnswer(8)
  *
  *   8 represents number that user chose
  *
  * @param questionText
  * @param questionDescription
  * @param min in above example, number 0
  * @param max in above example, number 10
  * @param minLabel optional parameter, in above example Some('not satisified at all')
  * @param maxLabel optional parameer, in above example Some('very satisfied')
  */
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
