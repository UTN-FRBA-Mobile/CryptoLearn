package com.mobile.test.model

data class Question(
    val questionTitle: String,
    val questionDescription: String?="",
    val options: MutableList<String>,
    val answerIndex: Int?,
    var selectedAnswer: Int? = null,
    var isCorrect: Boolean? = null
)