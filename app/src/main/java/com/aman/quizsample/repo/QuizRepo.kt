package com.aman.quizsample.repo

import com.aman.quizsample.room.database.AppDatabase
import com.aman.quizsample.room.entity.Quiz
import io.reactivex.Flowable

class QuizRepo(private val db: AppDatabase): QuizRepoI {
    override fun getAllItem(): Flowable<List<Quiz>> {
        return db.quizDao().getAllQuestion()
    }
}