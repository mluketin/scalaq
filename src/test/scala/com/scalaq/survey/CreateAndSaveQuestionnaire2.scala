package com.scalaq.survey

import com.scalaq.survey.database.DbAdapter
import com.scalaq.survey.model.Questionnaire
import com.scalaq.survey.model.question._
import org.scalatest.{FlatSpec, Matchers}

class CreateAndSaveQuestionnaire2 extends FlatSpec with Matchers {

  "Questionaire" should "be created and saved" in {

    //CREATING
    val q1 = TextInputQuestion("How old are you?", None)
    val questionnaire = Questionnaire("Test", None, Array(q1))

    //SAVING
    DbAdapter.saveQuestionnaire(questionnaire = questionnaire)
  }
}
