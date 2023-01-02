package com.example.taskmanagement

import android.app.Application
import com.example.taskmanagement.di.DaggerAppComponent

class App : Application() {

    val component by lazy(LazyThreadSafetyMode.NONE){
        DaggerAppComponent.create()
    }
}