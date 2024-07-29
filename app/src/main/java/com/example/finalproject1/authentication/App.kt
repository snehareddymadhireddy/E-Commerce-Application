package com.example.finalproject1.authentication

import android.app.Application

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        SecuredSharedPreferenceManager.init(this)
    }
}