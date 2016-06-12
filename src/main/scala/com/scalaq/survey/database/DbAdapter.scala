package com.scalaq.survey.database

import java.util

import com.scalaq.survey.model.answer.Answer
import com.scalaq.survey.model.question.Question
import com.scalaq.survey.model.questionnaire.{CompletedQuestionnaire, Questionnaire}
import org.revenj.patterns.Specification

import scalaq.persistence.repositories.{CompletedQuestionnaireRepository, QuestionnaireRepository}
import scalaq.{Boot, persistence}
import scala.collection.JavaConverters._

/**
  * This class contains static methods for interacting with database
  */
object DbAdapter {
  val locator = Boot.configure("jdbc:postgresql://localhost:5432/scalaq?user=scalaq&password=scalaq")
  val questionnaireRepository = locator.resolve(classOf[QuestionnaireRepository])
  val completedQuestionnaireRepository = locator.resolve(classOf[CompletedQuestionnaireRepository])

  /**
    * Saves questionnaire into database
    * @param questionnaire
    * @return
    */
  def saveQuestionnaire(questionnaire: Questionnaire): persistence.Questionnaire = {
    questionnaireRepository.insert(scalaToJavaQuestionnaire(questionnaire))
    return getQuestionnaire(questionnaire)
  }

  /**
    * Returns persistence questionnaire object for model questionnaire
    * @param questionnaire
    * @return
    */
  def getQuestionnaire(questionnaire: Questionnaire): persistence.Questionnaire = {
    for(q <- questionnaireRepository.search().asScala) {
      if(q.getName == questionnaire.name) {
        val desc = if(questionnaire.description == None) "" else questionnaire.description.get
        if(q.getDescription == desc) {
          return q
        }
      }
    }
    return null
  }

  /**
    * Returns java list of questionnaires that have same name as param name
    */
  def getQuestionnaires(name: String): java.util.List[persistence.Questionnaire] = {
    val hasName = new persistence.Questionnaire.hasName(name)
    questionnaireRepository.search(hasName)
  }


  /**
    * Returns java list of questionnaires that have same name and description params
    */
  def getQuestionnaires(name: String, description: String): java.util.List[persistence.Questionnaire] = {
    val hasNameAndDescription = new persistence.Questionnaire.hasNameAndDescription(name, description)
    questionnaireRepository.search(hasNameAndDescription)
  }

  /**
    * Updates questionnaire
    * @param oldQuestionaire old model questionnaire
    * @param newQuestionnaire new model questionnaire
    */
  def updateQuestionnaire(oldQuestionaire: Questionnaire, newQuestionnaire: Questionnaire): Unit = {

    val pq = getPersistanceQuestionnaire(oldQuestionaire)
    pq.setName(newQuestionnaire.name)
    pq.setDescription(if (newQuestionnaire.description == None) "" else newQuestionnaire.description.get)
    pq.setQuestions(scalaToJavaQuestions(newQuestionnaire.questions))

    questionnaireRepository.update(pq)


//    questionnaireRepository.update(scalaToJavaQuestionnaire(questionnaire))
  }

  /**
    * Deletes questionnaire
    * @param questionnaire model questionnaire
    */
  def deleteQuestionnaire(questionnaire: Questionnaire): Unit = {
    questionnaireRepository.delete(getPersistanceQuestionnaire(questionnaire))
  }

  /**
    * Deletes questionnaire
    * @param questionnaire persistence questionnaire
    */
  def deleteQuestionnaire(questionnaire: persistence.Questionnaire): Unit = {
    questionnaireRepository.delete(questionnaire)
  }

  /**
    * Saves completed questionnaire
    * @param completedQuestionnaire model completed questionnaire
    * @return
    */
  def saveCompletedQuestionnaire(completedQuestionnaire: CompletedQuestionnaire) = {
    val cq = new persistence.CompletedQuestionnaire()
      .setQuestionnaire(getPersistanceQuestionnaire(completedQuestionnaire.questionnaire))
      .setAnswers(scalaToJavaAnswers(completedQuestionnaire.answers))

    completedQuestionnaireRepository.insert(cq)
  }

  /**
    * converts model questionnaire into persistance questionnaire
    * @param questionnaire
    * @return
    */
  private def scalaToJavaQuestionnaire(questionnaire: Questionnaire): persistence.Questionnaire = {
    new persistence.Questionnaire()
      .setName(questionnaire.name)
      .setDescription(if (questionnaire.description == None) "" else questionnaire.description.get)
      .setQuestions(scalaToJavaQuestions(questionnaire.questions))
  }

  /**
    * converts sequence of model questions into list of persistance question
    * @param questions
    * @return
    */
  private def scalaToJavaQuestions(questions: Seq[Question]): util.List[persistence.Question] = {
    scala.collection.JavaConversions.seqAsJavaList(for (q <- questions) yield q.getPersistanceQuestion())
  }

  /**
    * converts sequence of model answers into list of persistence answers
    * @param answers
    * @return
    */
  private def scalaToJavaAnswers(answers: Seq[Answer]): util.List[persistence.Answer] = {
    scala.collection.JavaConversions.seqAsJavaList(for (a <- answers) yield a.getPersistenceAnswer())
  }

  /**
    * gets persistence questionnaire for model questionnaire
    * @param q
    * @return
    */
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

  /**
    * returns srquence of all completed questionaires for questionnaire passed as param q
    * @param q
    * @return
    */
  def getCompletedQuestionnaires(q: persistence.Questionnaire): Seq[persistence.CompletedQuestionnaire] = {
    for(cq <- completedQuestionnaireRepository.search().asScala
      if cq.getQuestionnaire == q)
      yield cq
  }
}
