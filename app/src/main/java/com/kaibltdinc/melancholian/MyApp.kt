package com.kaibltdinc.melancholian


import android.app.Application


class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin(this)
    }
}
