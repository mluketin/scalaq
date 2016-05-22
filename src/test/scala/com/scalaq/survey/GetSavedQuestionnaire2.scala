package com.scalaq.survey

import com.scalaq.survey.database.DbAdapter
import com.scalaq.survey.model.Questionnaire
import com.scalaq.survey.model.question._
import org.scalatest.{FlatSpec, Matchers}


class GetSavedQuestionnaire2 extends FlatSpec with Matchers {


  def createQuestionnaire(): Questionnaire = {
    val q1 = TextInputQuestion("How old are you?", None)
    Questionnaire("Test Questionnaire", None, Array(q1))
  }

  "Questionaire" should "be created and saved" in {

    val q = createQuestionnaire()

    val saved = DbAdapter.getPersistanceQuestionnaire(q)
    println(saved.getName)
    println(saved.getDescription)
    println(saved.getQuestions)
  }

}
