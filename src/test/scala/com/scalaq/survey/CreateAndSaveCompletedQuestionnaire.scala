//package com.scalaq.survey
//
//import com.scalaq.survey.database.DbAdapter
//import com.scalaq.survey.model.CompletedQuestionnaire
//import com.scalaq.survey.model.question._
//import com.scalaq.survey.model.questionnaire.{CompletedQuestionnaire, Questionnaire}
//import org.scalatest.{FlatSpec, Matchers}
//
//
//class CreateAndSaveCompletedQuestionnaire extends FlatSpec with Matchers {
//
//  def createQuestionnaire(): Questionnaire = {
//
////    val q1 = TextInputQuestion("How old are you?", None)
//
//    val occupationsArray = Array("unemployed", "student", "worker", "retired")
//    val q2 = SingleSelectQuestion("Occupation", None, occupationsArray)
//
//    val seriesArray = Array("Arrow", "Flash", "The 100", "Supernatural")
//    val q3 = MultiSelectQuestion("What series are you watching?", None, seriesArray)
//
//    val q4 = OrdinalScaleQuestion("How much are you satisfied with internet speed?", None, 1, 5, Some("Not satisfied"), Some("Very satisfied"))
//
//    val rows = Array("1", "2", "3", "4")
//    val columns = Array("1", "4", "9", "16")
//    val q5 = MatrixQuestion("Select correct numbe square", None, rows, columns)
//
////    Questionnaire("Test Questionnaire", None, Array(q1, q2, q3, q4, q5))
//    Questionnaire("Test Questionnaire", None, Array(q2, q3, q4, q5))
//  }
//
//  def completeQuestionnaire1(questionnaire: Questionnaire): CompletedQuestionnaire = {
//    val questions = questionnaire.questions
//    val answers = for {question <- questions} yield question.getRandomAnswer()
//
//    CompletedQuestionnaire(questionnaire, answers)
//  }
//
//  "Questionaire" should "be created and saved" in {
//
//    val q = createQuestionnaire()
//
//    val cq = completeQuestionnaire1(q)
//
//    DbAdapter.saveCompletedQuestionnaire(cq)
//  }
//
//}
