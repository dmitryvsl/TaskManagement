package com.example.taskmanagement

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler { e ->
            if (e is UndeliverableException)
                Log.e("App", e.message!!)
             else
                Thread.currentThread().also { thread ->
                    thread.uncaughtExceptionHandler?.uncaughtException(thread, e)
                }
            }
    }
}