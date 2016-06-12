package com.scalaq.survey.converter

import java.util

import com.scalaq.survey.model.answer.Answer
import com.scalaq.survey.model.question.Question

import scalaq.persistence


object ScalaToJavaConverter {

  /**
    * converts sequence of model questions into list of persistance question
    *
    * @param questions
    * @return
    */
  def scalaToJavaQuestions(questions: Seq[Question]): util.List[persistence.Question] = {
    scala.collection.JavaConversions.seqAsJavaList(for (q <- questions) yield q.getPersistanceQuestion())
  }

  /**
    * converts sequence of model answers into list of persistence answers
    *
    * @param answers
    * @return
    */
  def scalaToJavaAnswers(answers: Seq[Answer]): util.List[persistence.Answer] = {
    scala.collection.JavaConversions.seqAsJavaList(for (a <- answers) yield a.getPersistenceAnswer())
  }

}
