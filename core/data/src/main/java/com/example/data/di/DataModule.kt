package com.example.data.di

import com.example.cache.datasource.LocalUserDataSource
import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.ProjectRepositoryImpl
import com.example.domain.repository.*
import com.example.network.datasource.RemoteProjectDataSource
import com.example.network.datasource.RemoteUserDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideAuthRepository(
        dataSource: RemoteUserDataSource, localUserDataSource: LocalUserDataSource
    ): AuthRepository = AuthRepositoryImpl(dataSource, localUserDataSource)

    @Provides
    fun provideMutableAuthRepository(
        dataSource: RemoteUserDataSource, localUserDataSource: LocalUserDataSource
    ): MutableAuthRepository = AuthRepositoryImpl(dataSource, localUserDataSource)

    @Provides
    fun provideUserSignInCheckRepository(
        dataSource: RemoteUserDataSource, localUserDataSource: LocalUserDataSource
    ): UserSignInCheckRepository = AuthRepositoryImpl(dataSource, localUserDataSource)

    @Provides
    fun provideProjectRepository(
        remoteDataSource: RemoteProjectDataSource, localUserDataSource: LocalUserDataSource
    ): ProjectRepository = ProjectRepositoryImpl(remoteDataSource, localUserDataSource)

}