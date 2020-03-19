package com.aman.quizsample.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Entity(tableName = "quiz")
@Parcelize
data class Quiz(

    @ColumnInfo(name = "answers")
    @SerializedName("answers")
    @TypeConverters(QuizTypeConverter::class)
    val answers: @RawValue List<String>,

    @ColumnInfo(name = "correctIndex")
    @SerializedName("correctIndex")
    val correctIndex: Int,

    @ColumnInfo(name = "question")
    @SerializedName("question")
    val question: String


): Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    constructor(): this(answers = listOf(), correctIndex = 0, question = "")

}