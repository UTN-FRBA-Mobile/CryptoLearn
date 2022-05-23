package com.mobile.test.model

data class Chapter(
    val name: String,
    val url: String,
    val image: String,
    val status: String,
    val questions: MutableList<Question>
)