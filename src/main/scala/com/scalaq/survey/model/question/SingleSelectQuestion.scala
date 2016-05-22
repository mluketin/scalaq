package com.scalaq.survey.model.question

import com.scalaq.survey.model._
import java.util.Random

import com.scalaq.survey.model.answer.{Answer, SingleAnswer}

import scalaq.persistence
import scalaq.persistence.SingleSelectQuestion


//radio botuns, combo box
case class SingleSelectQuestion(questionText: String, questionDescription: Option[String], offeredAnswers: Seq[String]) extends Question {
  def getRandomAnswer(): Answer = {
    val rand = new Random()
    val random_index = rand.nextInt(offeredAnswers.length)
    SingleAnswer(random_index)
  }

  def getExportData(answers: Map[Question, Map[Answer, Int]]): Array[Array[Object]] = {
    val header: Array[Object] = Array("Answers:", "#")
    val map = answers.get(this).get
    val data = new Array[Array[Object]](offeredAnswers.size + 1)
    data(0) = header

    var br = 1
    for (offeredAnswer <- offeredAnswers) {
      data(br) = Array(offeredAnswer,
        map.getOrElse(SingleAnswer(br - 1), 0).asInstanceOf[Object])
      br += 1
    }
    data
  }

  override def getPersistanceQuestion(): persistence.Question = {
    val q: scalaq.persistence.Question = new persistence.Question()
    q.setBody(questionText)
    q.setDescription(if (questionDescription == None) "" else questionDescription.get)
    val singleSelectQuestionSpec = new persistence.SingleSelectQuestion()
      .setOfferedAnswers(scala.collection.JavaConversions.seqAsJavaList(offeredAnswers))
    q.setSpec(singleSelectQuestionSpec)
    //TODO ovo gore odkomentiraj kad se testira
    return q
  }
}
