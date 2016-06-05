package com.scalaq.survey.model.answer

import java.util

import scalaq.persistence

/**
  *   Sequence is size of rows in question, and each entry represents index of column marked (or null if no column is marked in row)
  *   Example:
  *
  *   Do you like:
  *           YES    NO
  *   Apple    x
  *   Kiwi            x
  *   Nutella  x
  *   Puding
  *   Pineapple       x
  *
  *   if you mark answers like above, you make multiple answer as follows:
  *
  *     val answer = MultipleAnswer(Seq(0, 1, 0, -1, 1))
  *
  *     0 represents marking column YES, 1 represents marking column NO, and -1 represents marking none of offered answers
  *
  */
case class MatrixAnswer(answer: Seq[Int]) extends Answer{

  def getPersistenceAnswer(): persistence.Answer = {
    val javaList: java.util.List[Integer] = new util.ArrayList[Integer]()
    for (ans <- answer) {
      javaList.add(ans.asInstanceOf[Integer])
    }

    val a = new persistence.Answer()
    val spec = new persistence.MatrixAnswer().setSelectedList(javaList)
    a.setSpec(spec)
    return a
  }
}
