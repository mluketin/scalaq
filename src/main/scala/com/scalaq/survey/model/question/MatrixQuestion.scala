package com.scalaq.survey.model.question

import java.util.Random

import com.scalaq.survey.model.answer.{Answer, MultipleAnswer}

import scalaq.persistence

case class MatrixQuestion(questionText: String, questionDescription: Option[String], rows: Seq[String], columns: Seq[String]) extends Question {
  def getRandomAnswer(): Answer = {
    val rand = new Random()
    val answers = new Array[Int](rows.length)
    var br = 0
    while (br < rows.length) {
      val random_index = rand.nextInt(columns.length)
      answers(br) = random_index
      br += 1
    }
    MultipleAnswer(answers)
  }

  //TODO
  def getExportData(answers: Map[Question, Map[Answer, Int]]): Array[Array[Object]] = {
    val data: Array[Array[Object]] = null
    data
  }

  override def getPersistanceQuestion(): persistence.Question = {
    val q: scalaq.persistence.Question = new persistence.Question()
    q.setBody(questionText)
    q.setDescription(if (questionDescription == None) "" else questionDescription.get)
    val matrixQuestionSpec = new persistence.MatrixQuestion()
      .setRows(scala.collection.JavaConversions.seqAsJavaList(rows))
      .setColumns(scala.collection.JavaConversions.seqAsJavaList(columns))
    q.setSpec(matrixQuestionSpec)
    //TODO ovo gore odkomentiraj kad se testira
    return q
  }
}
