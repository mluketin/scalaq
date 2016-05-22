package com.scalaq.survey

import java.util

import com.scalaq.survey.export.Mapper
import com.scalaq.survey.model._
import com.scalaq.survey.model.question._
import org.scalatest.{FlatSpec, Matchers}

class CreateQuestionsTest2 extends FlatSpec with Matchers {

  def createQuestionnaire(): Questionnaire = {

//    val q1 = TextInputQuestion("How old are you?", None)

    val occupationsArray = Array("unemployed", "student", "worker", "retired")
    val q2 = SingleSelectQuestion("Occupation", None, occupationsArray)

    val seriesArray = Array("Arrow", "Flash", "The 100", "Supernatural")
    val q3 = MultiSelectQuestion("What series are you watching?", None, seriesArray)

    val q4 = OrdinalScaleQuestion("How much are you satisfied with internet speed?", None, 1, 5, Some("Not satisfied"), Some("Very satisfied"))

    val rows = Array("1", "2", "3", "4")
    val columns = Array("1", "4", "9", "16")
    val q5 = MatrixQuestion("Select correct numbe square", None, rows, columns)

    Questionnaire("Test Questionnaire", None, Array(q2, q3, q4, q5))
//    Questionnaire("Test Questionnaire", None, Array(q1, q2, q3, q4, q5))
  }

  def completeQuestionnaire1(questionnaire: Questionnaire): CompletedQuestionnaire = {

    val questions = questionnaire.questions
    val answers = for {question <- questions} yield question.getRandomAnswer()
    return CompletedQuestionnaire(questionnaire, answers)
  }

  "Questionaire" should "be created" in {
    val questionnaire = createQuestionnaire()

//    val list = new Array[CompletedQuestionnaire](100)

//    var counter = 0
//    while(counter < 100) {
//      list.update(counter, completeQuestionnaire1(questionnaire))
//      counter += 1
//    }

//    val listAsSeq = list.toSeq

    val completedQuestionnaire1 = completeQuestionnaire1(questionnaire)
    val completedQuestionnaire2 = completeQuestionnaire1(questionnaire)
    val completedQuestionnaire3 = completeQuestionnaire1(questionnaire)
    val completedQuestionnaire4 = completeQuestionnaire1(questionnaire)
    val completedQuestionnaire5 = completeQuestionnaire1(questionnaire)

    val completedQuestionnaires = Seq(completedQuestionnaire1, completedQuestionnaire2, completedQuestionnaire3, completedQuestionnaire4, completedQuestionnaire5)
//    val completedQuestionnaires = Seq(completedQuestionnaire1)



    val mapper = new Mapper(completedQuestionnaire1.questionnaire)
    mapper.mapResults(completedQuestionnaires)
//    mapper.mapResults(listAsSeq)
//    mapper.exportXlsx()
      mapper.exportDocx()
  }
}
