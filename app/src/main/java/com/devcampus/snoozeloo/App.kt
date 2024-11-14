package com.devcampus.snoozeloo

import android.app.Application
import androidx.room.Room
import com.devcampus.snoozeloo.repository.room.AlarmDatabase
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    lateinit var database: AlarmDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        configureLogging()
        configureRoom()
    }

    private fun configureLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // TODO Add crashlytics
        }
    }

    private fun configureRoom() {
        database = Room.databaseBuilder(
            this,
            AlarmDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    companion object {
        private const val DATABASE_NAME = "alarm_database"
    }
}