package com.sg.challengechap2

import android.app.Application
import com.sg.challengechap2.data.local.database.AppDatabase

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}