package com.example.android.guesstheword

import android.app.Application
import timber.log.Timber

class GuessItApplication: Application() {
    override fun onCreate() {
        val timber = Timber.plant(Timber.DebugTree())
        super.onCreate()
    }
}