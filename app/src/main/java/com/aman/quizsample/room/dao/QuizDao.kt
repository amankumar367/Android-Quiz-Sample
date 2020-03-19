package com.aman.quizsample.room.dao

import androidx.room.*
import com.aman.quizsample.room.entity.Quiz
import io.reactivex.Flowable

@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(quizzes: List<Quiz>)

    @Query("SELECT * FROM QUIZ")
    fun getAllQuestion(): Flowable<List<Quiz>>

}