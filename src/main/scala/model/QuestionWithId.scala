package model

case class QuestionWithId(id: String, question: String)

object QuestionWithId {

  def apply(id: String, question: Question): QuestionWithId = new QuestionWithId(id, question.question)

}
