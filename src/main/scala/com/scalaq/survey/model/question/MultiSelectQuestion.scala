package com.scalaq.survey.model.question

import java.util.Random

import com.scalaq.survey.model.answer.{Answer, MultipleAnswer}

import scalaq.persistence

//check box
case class MultiSelectQuestion(questionText: String, questionDescription: Option[String], offeredAnswers: Seq[String]) extends Question {
  def getRandomAnswer(): Answer = {
    val rand = new Random()

    val numOfAnswers = rand.nextInt(offeredAnswers.length - 1) + 1
    var set: Set[Int] = Set()
    while (set.size != numOfAnswers) {
      val random_index = rand.nextInt(offeredAnswers.length)
      set += random_index
    }
    val seqInt = set.toSeq
    MultipleAnswer(seqInt)
  }

  def getExportData(answers: Map[Question, Map[Answer, Int]]): Array[Array[Object]] = {
    val header: Array[Object] = Array("Answers:", "#")
    val map = answers.get(this).get
    val data = new Array[Array[Object]](map.size + 1)
    data(0) = header

    var br = 1
    for (entry <- map) {
      var stringBuilder = ""
      for (a <- entry._1.answer.asInstanceOf[Seq[Int]]) {
        stringBuilder += a + "\n"
      }
      println(stringBuilder.length)
      stringBuilder.substring(0, stringBuilder.length - 1) //to remove last "\n"
      data(br) = Array(stringBuilder, entry._2.asInstanceOf[Object])
      br += 1
    }
    data
  }

  override def getPersistanceQuestion(): persistence.Question = {
    val q: scalaq.persistence.Question = new persistence.Question()
    q.setBody(questionText)
    q.setDescription(if (questionDescription == None) "" else questionDescription.get)
    val multiSelectQuestionSpec = new persistence.MultiSelectQuestion()
      .setOfferedAnswers(scala.collection.JavaConversions.seqAsJavaList(offeredAnswers))
    q.setSpec(multiSelectQuestionSpec)
    //TODO ovo gore odkomentiraj kad se testira
    return q
  }

}
