package com.scalaq.survey.export

import java.awt.Desktop
import java.io.{File, FileOutputStream}

import com.scalaq.survey.model.answer.{Answer, Unanswered}
import com.scalaq.survey.model.questionnaire.{CompletedQuestionnaire, Questionnaire}
import com.scalaq.survey.model.question
import hr.ngs.templater.Configuration

/**
  * This class is used for mapping all answers from completed questionnaires for certain questionnaire.
  * After mapping results, export can be done into xlsx and docx file formats
  * @param quest questionnaire for which mapping will be made
  */
class Mapper(quest: Questionnaire) {
  private var questionnaire: Questionnaire = null
  private var exportMap = Map[question.Question, Map[Answer, Int]]()

  for (q <- quest.questions) {
    exportMap += (q -> Map[Answer, Int]())
  }

  /**
    * Adds answers from completedQuestionnaires into map
    * This method calls mapResults method for each completed questionaire in passed seqeuence
    * @param completedQuestionnaires
    */
  def mapResults(completedQuestionnaires: Seq[CompletedQuestionnaire]): Unit = {
    for (completedQuestionnaire <- completedQuestionnaires) {
      mapResults(completedQuestionnaire)
    }
  }

  /**
    * Adds answers from completedQuestionaire into map
    * @param completedQuestionnaire
    */
  def mapResults(completedQuestionnaire: CompletedQuestionnaire): Unit = {
    if (questionnaire == null) {
      questionnaire = completedQuestionnaire.questionnaire
    } else if (!questionnaire.equals(completedQuestionnaire.questionnaire)) {
      throw new RuntimeException("One mapper object cannot work with different questionnaires")
    }

    var counter = 0
    for (answers <- completedQuestionnaire.answers) {
      if(!answers.isInstanceOf[Unanswered]) {
        var map: Map[Answer, Int] = exportMap.get(questionnaire.questions(counter)).get
        val newValue = map.getOrElse(answers, 0) + 1
        map += (answers -> newValue)
        exportMap += (questionnaire.questions(counter) -> map)
        counter += 1
      }
    }
  }

  /**
    * Objects of this class are used for making exported document
    * @param qName
    * @param qDescription
    * @param qData
    * @param note
    */
  case class ExportObjectClass(qName: String, qDescription: String, qData: Array[Array[Object]], note: String)

  /**
    * Exports mapped results into xlsx file format
    * This method calles ExportXlsx method whic takes export objects as parameter
    */
  def exportXlsx(): File = {
    exportXlsx(convertToExportObjects())
  }

  /**
    * Exports mapped results into xlsx file format
    * @param exportObjects
    */


  def exportXlsx(exportObjects: Seq[ExportObjectClass]): File = {
    case class Report(name: String, description: String, question: Seq[ExportObjectClass])

    val name = questionnaire.name
    val description = if(questionnaire.description == None) "" else questionnaire.description.get
    val report = Report(name, description, exportObjects)

    val templateStream = getClass.getResourceAsStream("/Template.xlsx")
    val tmp: File = File.createTempFile("test", ".xlsx")
    val output: FileOutputStream = new FileOutputStream(tmp)
    val tpl = Configuration.factory().open(templateStream, "xlsx", output)
    tpl.process(report)
    tpl.flush()
    templateStream.close()
    output.close()
    tmp
  }

  /**
    * Exports mapped results into docx file format
    * This method calles ExportDocx method whic takes export objects as parameter
    */
  def exportDocx(): File = {
    exportDocx(convertToExportObjects())
  }

  /**
    * Exports mapped results into docx file format
    * @param exportObjects
    */
  def exportDocx(exportObjects: Seq[ExportObjectClass]): File = {
    case class Report(name: String, description: String, question: Seq[ExportObjectClass])

    val name = questionnaire.name
    val description = if(questionnaire.description == None) "" else questionnaire.description.get
    val report = Report(name, description, exportObjects)

    val templateStream = getClass.getResourceAsStream("/Template.docx")
    val tmp: File = File.createTempFile("test", ".docx")
    val output: FileOutputStream = new FileOutputStream(tmp)
    val tpl = Configuration.factory().open(templateStream, "docx", output)
    tpl.process(report)
    tpl.flush()
    templateStream.close()
    output.close()
    tmp
  }

  /**
    * For each question in questionnaire, this method calles
    * question.getExportData(exportMap) and passes exportMap where mapper saves results of mapping
    * @return returns sequence of export objects
    */
  def convertToExportObjects(): Seq[ExportObjectClass] = {
    for (question <- questionnaire.questions)
      yield ExportObjectClass(
        question.questionText,
        if (question.questionDescription == None) "" else question.questionDescription.get,
        question.getExportData(exportMap),
        "")
  }
}
