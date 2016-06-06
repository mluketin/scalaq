package com.scalaq.survey.model.question

import com.scalaq.survey.model.answer.Answer

/**
  * Trait that all question type classes must extend.
  * If you want to make your own type of question,
  * than your class must extend this trait and implement its methods.
  */
trait Question {

  /**
    * Defines question text
    */
  def questionText: String

  /**
    * Optional description for answer.
    */
  def questionDescription: Option[String]

  /**
    * Defines method that returns export data for that question.
    * This method is used in Mapper class.
    * @param questionMap map that for each question in questionnaire Question contains map of answers for that question
    * @return returns object whis is passed to templater for exporting data into files
    */
  def getExportData(questionMap: Map[Question, Map[Answer, Int]]): Array[Array[Object]]

  /**
    * Returns persistance Question class that is used for managing database.
    * This persistence classes are containt in scalaq-model-sources.jar and that jar is generated when applying migration script
    * @return persistence equivalent of question
    */
  def getPersistanceQuestion(): scalaq.persistence.Question

  /**
    * For testing purposes
    */
  def getRandomAnswer(): Answer
}











