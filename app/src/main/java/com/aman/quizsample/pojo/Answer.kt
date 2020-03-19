package com.aman.quizsample.pojo

data class Answer(
    val question: String,
    val selectedPosition: Int,
    var answerIndex: Int,
    val correctIndex: Int
)