package com.example.samplekotlinapplication

import android.app.Application
import android.content.Context

class App : Application() {
    private var appContext: Context? = null

    private var instance: App? = null


    override fun onCreate() {
        super.onCreate()
        appContext = this
        instance = this


    }


}