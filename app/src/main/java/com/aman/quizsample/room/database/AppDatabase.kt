package com.aman.quizsample.room.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.aman.quizsample.room.dao.QuizDao
import com.aman.quizsample.room.entity.Quiz
import com.aman.quizsample.room.entity.QuizTypeConverter
import com.aman.quizsample.worker.DataInitializer

@Database(entities = [Quiz::class], version = 1, exportSchema = false)
@TypeConverters(QuizTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun quizDao(): QuizDao

    companion object {
        private const val TAG = "AppDatabase"
        private const val DATABASE_NAME = "quiz_application.db"

        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(this) {
                    Log.d(TAG, " >>> Creating new database instance")
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    ).addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.d(TAG, " >>> DB has been created")
                            val request = OneTimeWorkRequestBuilder<DataInitializer>().build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }).build()
                }
            }
            Log.d(TAG, " >>> Getting the database instance")
            return INSTANCE
        }
    }
}