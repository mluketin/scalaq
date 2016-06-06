package com.scalaq.survey.model.question

import java.util.Random

import com.scalaq.survey.model.answer.{Answer, MatrixAnswer}

import scalaq.persistence

/**
  * Used for questions that have tables
  *
  * Example
  *
  *  "How much do you like this tv shows?"
  *                 Dont like at all |   OK    | Like very much
  *  Supernatural                         X
  *  Arrow                  X
  *  Flash                                              X
  *  Supergirl
  *  The 100                               X
  *
  *  X mark represents what user marked as answer
  *
  *  Answer for this example will be
  *  MatrixAnswer(Seq(1, 0, 2, -1, 1))
  *
  *  answer order represents rows
  *  value represents index of chosen column (-1 represents not chosing anything as answer)
  *
  *  so first number is 1, that means first row, and second column (indexing of column is from 0)
  *  second number is 0, that means second row, and first column
  *  etc
  *
  *
  * @param questionText
  * @param questionDescription
  * @param rows
  * @param columns
  */
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
    MatrixAnswer(answers)
  }

  def getExportData(answers: Map[Question, Map[Answer, Int]]): Array[Array[Object]] = {
    //simplifying answers
    val map = answers.get(this).get
    val answersArray: Array[Array[Int]] = new Array[Array[Int]](rows.length)
    for(i <- 0 until answersArray.length) {
      answersArray(i) = new Array[Int](columns.length)
    }

    for(item <- map) {

      val a = item._1.asInstanceOf[MatrixAnswer]
      val numberOfOccurance = item._2

      for(i <- 0 until a.answer.size) {
          if(a.answer(i) != -1) {
            answersArray(i)(a.answer(i)) += numberOfOccurance
          }
      }
    }


    val n = rows.length+1
    val m = columns.length+1

    val data = new Array[Array[Object]](n)

    //first row
    data(0) = new Array[Object](m)
    for(i <- 0 until columns.length){
      data(0)(i+1) = columns(i)
    }

    //other rows
    for(i <- 1 until n) {
      data(i) = new Array[Object](m)
      data(i)(0) = rows(i-1)

      for(j <- 0 until m-1) {
        data(i)(j+1) = answersArray(i-1)(j).asInstanceOf[Object]
      }
    }
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
    return q
  }
}
