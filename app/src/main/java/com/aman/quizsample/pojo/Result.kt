package com.aman.quizsample.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result(
    val totalQuestion: Int,
    val attemptedQuestion: Int,
    val correctAnswer: Int,
    val wrongAnswer: Int,
    val skippedQuestion: Int
) : Parcelable