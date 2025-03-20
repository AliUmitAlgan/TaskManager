package com.aliumitalgan.taskmanager

import android.app.Application
import com.google.firebase.BuildConfig
import timber.log.Timber
import com.google.firebase.FirebaseApp

class TaskManagerApp : Application() {

    companion object {
        // Singleton instance of the application
        private lateinit var instance: TaskManagerApp

        fun getInstance(): TaskManagerApp {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Setup Timber for logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}