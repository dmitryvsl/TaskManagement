package com.example.taskmanagement

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.taskmanagement.di.AppComponent
import com.example.taskmanagement.di.DaggerAppComponent

class App : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    val component: AppComponent by lazy(LazyThreadSafetyMode.NONE){
        DaggerAppComponent.create()
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }


}