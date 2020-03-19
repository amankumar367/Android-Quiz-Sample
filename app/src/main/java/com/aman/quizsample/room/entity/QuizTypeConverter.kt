package com.aman.quizsample.room.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class QuizTypeConverter {

    @TypeConverter
    fun toJSON(answers: List<String>): String {
        return Gson().toJson(answers)
    }

    @TypeConverter
    fun toStringList(answer: String): List<String> {
        val type = object :TypeToken<List<String>>(){}.type
        return Gson().fromJson(answer,type)
    }
}