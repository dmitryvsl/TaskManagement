package com.example.cache.di

import android.content.Context
import com.example.cache.UserStorage
import com.example.cache.datasource.LocalUserDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CacheModule {

    @Provides
    fun provideLocalUserDataSource(@ApplicationContext context: Context): LocalUserDataSource = UserStorage(context)
}