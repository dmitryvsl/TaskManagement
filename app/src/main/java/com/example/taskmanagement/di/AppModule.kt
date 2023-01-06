package com.example.taskmanagement.di

import android.content.Context
import android.content.SharedPreferences
import com.example.taskmanagement.App
import com.example.taskmanagement.utils.sharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
class AppModule {
    
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE)
}
