package com.mobile.test.model

data class Question(
    val question: String,
    val options: MutableList<String>,
    val answerIndex: Int
)