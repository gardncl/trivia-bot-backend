package model

import java.util.Date

case class AnsweredQuestion(id: Int, askedQuestionId: Int, answer: String, correct: Boolean, respondedAt: Date)
