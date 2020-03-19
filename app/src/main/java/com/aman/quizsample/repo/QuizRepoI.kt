package com.aman.quizsample.repo

import com.aman.quizsample.room.entity.Quiz
import io.reactivex.Flowable

interface QuizRepoI {
    fun getAllItem(): Flowable<List<Quiz>>
}