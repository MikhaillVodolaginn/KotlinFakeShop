package com.example.kotlinFakeShop

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KotlinFakeShop : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}