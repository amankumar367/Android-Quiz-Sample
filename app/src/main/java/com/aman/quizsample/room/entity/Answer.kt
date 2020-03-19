package com.aman.quizsample.room.entity

data class Answer(
    val question: String,
    val selectedPosition: Int,
    var answerIndex: Int,
    val correctIndex: Int
)