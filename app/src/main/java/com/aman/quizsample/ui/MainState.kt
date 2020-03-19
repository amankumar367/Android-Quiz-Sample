package com.aman.quizsample.ui

import com.aman.quizsample.room.entity.Quiz

data class MainState(
    var loading: Boolean = false,
    var success: Boolean = false,
    var failure: Boolean = false,
    var message: String? = null,
    var list: List<Quiz>? = listOf()
)