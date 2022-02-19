package com.beleavemebe.chrono.ui

import android.app.Application
import com.beleavemebe.chrono.db.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.initialize(this)
    }
}
