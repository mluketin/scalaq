package com.scalaq.survey

//import com.scalaq.survey.database.DbAdapter
import com.scalaq.survey.database.DbAdapter
import com.scalaq.survey.model.questionnaire
import com.scalaq.survey.model.question._
import com.scalaq.survey.model.questionnaire.Questionnaire
import org.scalatest.{FlatSpec, Matchers}

class CreateAndSaveQuestionnaire extends FlatSpec with Matchers {

  "Questionaire" should "be created and saved" in {

    //CREATING
    val q1 = TextInputQuestion("How old are you?", None)

    val occupationsArray = Array("unemployed", "student", "worker", "retired")
    val q2 = SingleSelectQuestion("Occupation", None, occupationsArray)

    val seriesArray = Array("Arrow", "Flash", "The 100", "Supernatural")
    val q3 = MultiSelectQuestion("What series are you watching?", None, seriesArray)

    val q4 = OrdinalScaleQuestion("How much are you satisfied with internet speed?", None, 1, 5, Some("Not satisfied"), Some("Very satisfied"))

    val rows = Array("1", "2", "3", "4")
    val columns = Array("1", "4", "9", "16")
    val q5 = MatrixQuestion("Select correct numbe square", None, rows, columns)

    val questionnaire = Questionnaire("Test Questionnaire", None, Array(q1, q2, q3, q4, q5))
//    val questionnaire = Questionnaire("Test Questionnaire", None, Array(q2, q3, q4, q5))

    //SAVING
    DbAdapter.saveQuestionnaire(questionnaire = questionnaire)
  }
}
