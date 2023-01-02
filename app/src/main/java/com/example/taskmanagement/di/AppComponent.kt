package com.example.taskmanagement.di

import com.example.taskmanagement.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)
}