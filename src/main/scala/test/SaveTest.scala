package test

import scalaq.{Boot, persistence}
import scalaq.persistence.repositories.QuestionnaireRepository


object SaveTest extends App {

  val locator = Boot.configure("jdbc:postgresql://localhost:5432/scalaq?user=scalaq&password=scalaq")
  val questionnaireRepository = locator.resolve(classOf[QuestionnaireRepository])

  //create question
  val question = new persistence.Question()
    .setBody("How old are you?")
    .setDescription("...")
    .setSpec(new persistence.TextInputQuestion())

  val list = scala.collection.JavaConversions.seqAsJavaList(Seq(question))

  //create questionnaire
  val questionnaire = new persistence.Questionnaire()
    .setName("Test questionnaire")
    .setDescription("Some Descript")
    .setQuestions(list)

  questionnaireRepository.insert(questionnaire)

  questionnaireRepository.search()
}
