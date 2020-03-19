package com.aman.quizsample

import android.util.Log
import com.aman.quizsample.di.DaggerAppComponent
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class QuizApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        Log.d(TAG, " >>> QuizApplication Created")

        if (BuildConfig.DEBUG) {
            Log.d(TAG, " >>> Initializing Stetho")
            Stetho.initializeWithDefaults(this)
        }

        return DaggerAppComponent.builder().application(this).build()
    }

    companion object {
        const val TAG = "QuizApplication"
    }
}