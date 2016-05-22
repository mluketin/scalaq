package test

import scalaq.Boot
import scalaq.persistence._
import scalaq.persistence.repositories._
import scala.collection.JavaConverters._

import java.util.Arrays


object Test  extends App{

//  val a: Option[String] = Some("asd")
//  val b = a.get
//
//  println(b)

//  val pitanja = Array("jedan", "dva", "tri")
//
//  val data = for {p <- pitanja
//       a = "asd"
//  } yield a
  //
  //  println(data)
///########$$$$$$$######################################################################
//  val questionnaire = new Questionnaire()
//    .setName("Mobiteli anketa")
//    .setDescription("Ovo je neka anketa")
//    .setQuestions(Arrays.asList(
//      new Question()
//        .setBody("Kod kojeg ste providera?")
//        .setDescription("Oznacite jednog providera ili upiseta svog pod ostalo")
//        .setSpec(new SingleSelectQuestion()
//          .setOfferedAnswers(Arrays.asList("Vip", "nesto", "ostalo"))),
//      new Question()
//        .setBody("Razmisljate li o promjeni operatera?")
//        .setDescription("Na kojeg prelazite")
//        .setSpec(new MultiSelectQuestion()
//          .setOfferedAnswers(Arrays.asList("Vip", "nesto", "ostalo", "jos neki")))
//    ))
  val locator = Boot.configure("jdbc:postgresql://localhost:5432/scalaq?user=scalaq&password=scalaq")
//
//    val questionnaire = new Questionnaire()
//      .setName("Neka anketa")
//      .setDescription("Ovo je neka anketa")
//      .setQuestions(Arrays.asList(
//        new Question()
//          .setBody("Kod kojeg ste providera?")
//          .setDescription("Oznacite jednog providera ili upiseta svog pod ostalo")
//          .setSpec(new SingleSelectQuestion()
//            .setOfferedAnswers(Arrays.asList("Vip", "nesto", "ostalo"))),
//        new Question()
//          .setBody("Razmisljate li o promjeni operatera?")
//          .setDescription("Na kojeg prelazite")
//          .setSpec(new MultiSelectQuestion()
//            .setOfferedAnswers(Arrays.asList("Vip", "nesto", "ostalo", "jos neki")))
//      ))

  val questionnaireRepository = locator.resolve(classOf[QuestionnaireRepository])
//
//  questionnaireRepository.insert(questionnaire)
//  println(questionnaireRepository.count())
  for(q <- questionnaireRepository.search().asScala) {
    println(q.getQuestions.size())
  }

}
