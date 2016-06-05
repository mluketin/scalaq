package com.scalaq.survey.model.question

import java.util.Random

import com.scalaq.survey.model.answer.{Answer, MultiSelectAnswer}

import scalaq.persistence

//check box
case class MultiSelectQuestion(questionText: String, questionDescription: Option[String], offeredAnswers: Seq[String], other: Option[String] = None) extends Question {
  def getRandomAnswer(): Answer = {
    val rand = new Random()

    val numOfAnswers = rand.nextInt(offeredAnswers.length - 1) + 1
    var set: Set[Int] = Set()
    while (set.size != numOfAnswers) {
      val random_index = rand.nextInt(offeredAnswers.length)
      set += random_index
    }
    val seqInt = set.toSeq
    MultiSelectAnswer(Some(seqInt))
  }

  def getExportData(answers: Map[Question, Map[Answer, Int]]): Array[Array[Object]] = {
    val map = answers.get(this).get

    var numberOfAnswers = 0
    var numberOfOfferedAnswers = 0
    for(v <- map.values) {
      numberOfAnswers += v
    }

    //count single answers
    val counters = new Array[Int](offeredAnswers.length)
    for(item <- map) {
      for(index <- item._1.asInstanceOf[MultiSelectAnswer].answer.get) {
        println(index)
        counters(index) += item._2
      }
    }

    val header: Array[Object] = Array("Answers:", "#")
    val data = new Array[Array[Object]](offeredAnswers.length + 1)
    data(0) = header

    for(br <- 0 until offeredAnswers.length) {
      data(br+1) = Array(offeredAnswers(br), counters(br).asInstanceOf[Object])
    }

//    for (entry <- map) {
//      var stringBuilder = ""
//      for (a <- entry._1.answer.asInstanceOf[Seq[Int]]) {
//        stringBuilder += a + "\n"
//      }
//      println(stringBuilder.length)
//      stringBuilder.substring(0, stringBuilder.length - 1) //to remove last "\n"
//      data(br) = Array(stringBuilder, entry._2.asInstanceOf[Object])
//      br += 1
//    }
    data
  }

  override def getPersistanceQuestion(): persistence.Question = {
    val q: scalaq.persistence.Question = new persistence.Question()
    q.setBody(questionText)
    q.setDescription(if (questionDescription == None) "" else questionDescription.get)
    val multiSelectQuestionSpec = new persistence.MultiSelectQuestion()
      .setOfferedAnswers(scala.collection.JavaConversions.seqAsJavaList(offeredAnswers))
      .setOther(if(other == None) "" else other.get)
    q.setSpec(multiSelectQuestionSpec)
    return q
  }

}
