package com.scalaq.survey.model.question

import java.util.Random

import com.scalaq.survey.model.answer.{Answer, SingleSelectAnswer}

import scalaq.persistence


//radio botuns, combo box
/**
  * Used for questions on which user can choose only one answer (radio bottun or combo box used for offering answers)
  * Question can also have 'Other' option on which user imputs his own answer
  *
  * Example:
  *
  *   "What is your favourite book?"
  *     - Harry Potter
  *     - Lord of the Rings
  *     - The Witcher
  *     - Write your own
  *
  *    if user choses this Other option than he will provide his own answer
  *
  *       Answer for that kind of question will be SingleSelectAnswer(None, Some("Eragon")) (if user imputs "Eragon" as his answer)
  *       If user choses one of offered answer, for example Harry Potter than answer will be: SingleSelectAnswer(Some(0), None)
  *       0 represents index of offered answer
  *
  *
  *
  * @param questionText
  * @param questionDescription
  * @param offeredAnswers in above example, Seq("Harry Potter", "Lord of the Rings", "The Witcher")
  * @param other oprional parameter, by default this is None; in above example, Some("Write your own")
  */
case class SingleSelectQuestion(questionText: String, questionDescription: Option[String], offeredAnswers: Seq[String], other: Option[String] = None) extends Question {
  def getRandomAnswer(): Answer = {
    val rand = new Random()
    val random_index = rand.nextInt(offeredAnswers.length)
    SingleSelectAnswer(Some(random_index))
  }

  def getExportData(answers: Map[Question, Map[Answer, Int]]): Array[Array[Object]] = {
    val header: Array[Object] = Array("Answers:", "#")
    val map = answers.get(this).get

    var numberOfAnswers = 0
    var numberOfOfferedAnswers = 0
    for(v <- map.values) {
      numberOfAnswers += v
    }

    val isOtherCount = if(other == None) 0 else 1
    val data = new Array[Array[Object]](offeredAnswers.size + 1 + isOtherCount)
    data(0) = header

    var br = 1
    for (offeredAnswer <- offeredAnswers) {
      data(br) = Array(offeredAnswer,
        map.getOrElse(SingleSelectAnswer(Some(br - 1)), 0).asInstanceOf[Object])
      numberOfOfferedAnswers += map.getOrElse(SingleSelectAnswer(Some(br - 1)), 0)
      br += 1
    }
    if(isOtherCount == 1) {
      val numberOfOtherAnswes = numberOfAnswers - numberOfOfferedAnswers
      data(br) = Array(other.get, numberOfOtherAnswes.asInstanceOf[Object])
    }
    data
  }

  override def getPersistanceQuestion(): persistence.Question = {
    val q: scalaq.persistence.Question = new persistence.Question()
    q.setBody(questionText)
    q.setDescription(if (questionDescription == None) "" else questionDescription.get)
    val singleSelectQuestionSpec = new persistence.SingleSelectQuestion()
      .setOfferedAnswers(scala.collection.JavaConversions.seqAsJavaList(offeredAnswers))
      .setOther(if(other == None) "" else other.get)
    q.setSpec(singleSelectQuestionSpec)
    return q
  }
}
