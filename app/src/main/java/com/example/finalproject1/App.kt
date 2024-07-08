package com.example.finalproject1

import android.app.Application

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        SecuredSharedPreferenceManager.init(this)
    }
}