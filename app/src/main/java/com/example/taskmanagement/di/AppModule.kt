package com.example.taskmanagement.di

import android.content.Context
import com.example.taskmanagement.App
import com.example.taskmanagement.utils.sharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(): Context = App.context

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context) =
        context.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE)
}