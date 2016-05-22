package com.scalaq.survey.database

import java.util

import com.scalaq.survey.model.answer.Answer
import com.scalaq.survey.model.{CompletedQuestionnaire, Questionnaire}
import com.scalaq.survey.model.question.Question

import scalaq.persistence.repositories.{CompletedQuestionnaireRepository, QuestionnaireRepository}
import scalaq.{Boot, persistence}
import scala.collection.JavaConverters._


object DbAdapter {
  val locator = Boot.configure("jdbc:postgresql://localhost:5432/scalaq?user=scalaq&password=scalaq")
  val questionnaireRepository = locator.resolve(classOf[QuestionnaireRepository])
  val completedQuestionnaireRepository = locator.resolve(classOf[CompletedQuestionnaireRepository])

  def saveQuestionnaire(questionnaire: Questionnaire): Unit = {
    questionnaireRepository.insert(scalaToJavaQuestionnaire(questionnaire))
  }

  def saveCompletedQuestionnaire(completedQuestionnaire: CompletedQuestionnaire) = {
    val cq = new persistence.CompletedQuestionnaire()
      .setQuestionnaire(getPersistanceQuestionnaire(completedQuestionnaire.questionnaire))
      .setAnswers(scalaToJavaAnswers(completedQuestionnaire.answers))

    completedQuestionnaireRepository.insert(cq)
  }

  private def scalaToJavaQuestionnaire(questionnaire: Questionnaire): persistence.Questionnaire = {
    new persistence.Questionnaire()
      .setName(questionnaire.name)
      .setDescription(if (questionnaire.description == None) "" else questionnaire.description.get)
      .setQuestions(scalaToJavaQuestions(questionnaire.questions))
  }

  private def scalaToJavaQuestions(questions: Seq[Question]): util.List[persistence.Question] = {
    scala.collection.JavaConversions.seqAsJavaList(for (q <- questions) yield q.getPersistanceQuestion())
  }

  private def scalaToJavaAnswers(answers: Seq[Answer]): util.List[persistence.Answer] = {
    scala.collection.JavaConversions.seqAsJavaList(for (a <- answers) yield a.getPersistenceAnswer())
  }

  def getPersistanceQuestionnaire(q: Questionnaire): persistence.Questionnaire = {
    //TODO WIP
    for (item <- questionnaireRepository.search().asScala) {
      if (item.getName == q.name) {
        val desc = if (q.description == None) "" else q.description.get
        if (desc == item.getDescription)
          return item
      }
    }
    return new persistence.Questionnaire()
  }
}
