package com.scalaq.survey.export

import java.awt.Desktop
import java.io.{File, FileOutputStream}

import com.scalaq.survey.model.answer.Answer
import com.scalaq.survey.model.{CompletedQuestionnaire, Questionnaire, question}
import hr.ngs.templater.Configuration


class Mapper(quest: Questionnaire) {
  private var questionnaire: Questionnaire = null
  private var questionMap = Map[Answer, Int]()
  private var exportMap = Map[question.Question, Map[Answer, Int]]()

  for (q <- quest.questions) {
    exportMap += (q -> Map[Answer, Int]())
  }

  def mapResults(completedQuestionnaires: Seq[CompletedQuestionnaire]): Unit = {
    for (completedQuestionnaire <- completedQuestionnaires) {
      mapResults(completedQuestionnaire)
    }
  }

  def mapResults(completedQuestionnaire: CompletedQuestionnaire): Unit = {
    if (questionnaire == null) {
      questionnaire = completedQuestionnaire.questionnaire
    } else if (!questionnaire.equals(completedQuestionnaire.questionnaire)) {
      throw new RuntimeException("One mapper object cannot work with different questionnaires")
    }

    var counter = 0
    for (answers <- completedQuestionnaire.answers) {
      var map: Map[Answer, Int] = exportMap.get(questionnaire.questions(counter)).get
      val newValue = map.getOrElse(answers, 0) + 1
      map += (answers -> newValue)
      exportMap += (questionnaire.questions(counter) -> map)
      counter += 1
    }
  }

  case class ExportObjectClass(qName: String, qDescription: String, qData: Array[Array[Object]], note: String)

  def exportXlsx(): Unit = {
    exportXlsx(convertToExportObjects())
  }

  def exportXlsx(exportObjects: Seq[ExportObjectClass]): Unit = {
    case class Report(question: Seq[ExportObjectClass])

    val report = Report(exportObjects)

    val templateStream = getClass.getResourceAsStream("/TemplateXlsx.xlsx")
    val tmp: File = File.createTempFile("test", ".xlsx")
    val output: FileOutputStream = new FileOutputStream(tmp)
    val tpl = Configuration.factory().open(templateStream, "xlsx", output)
    tpl.process(report)
    tpl.flush()
    templateStream.close()
    output.close()
    Desktop.getDesktop.open(tmp)
  }

  def exportDocx(): Unit = {
    exportDocx(convertToExportObjects())
  }

  def exportDocx(exportObjects: Seq[ExportObjectClass]): Unit = {
    case class Report(question: Seq[ExportObjectClass])

    val report = Report(exportObjects)

    val templateStream = getClass.getResourceAsStream("/TemplateDocx.docx")
    val tmp: File = File.createTempFile("test", ".docx")
    val output: FileOutputStream = new FileOutputStream(tmp)
    val tpl = Configuration.factory().open(templateStream, "docx", output)
    tpl.process(report)
    tpl.flush()
    templateStream.close()
    output.close()
    Desktop.getDesktop.open(tmp)
  }

  private def convertToExportObjects(): Seq[ExportObjectClass] = {
    for (question <- questionnaire.questions)
      yield ExportObjectClass(
        question.questionText,
        if (question.questionDescription == None) "" else question.questionDescription.get,
        question.getExportData(exportMap),
        "")
  }
}
