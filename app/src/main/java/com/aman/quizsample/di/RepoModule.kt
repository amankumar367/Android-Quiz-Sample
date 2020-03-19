package com.aman.quizsample.di

import com.aman.quizsample.repo.QuizRepo
import com.aman.quizsample.repo.QuizRepoI
import com.aman.quizsample.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    @Singleton
    fun provideQuizRepo(appDatabase: AppDatabase): QuizRepoI {
        return QuizRepo(appDatabase)
    }
}