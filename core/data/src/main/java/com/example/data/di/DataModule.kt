package com.example.data.di

import com.example.data.repository.AuthRepositoryImpl
import com.example.data.utils.ConnectivityManagerNetworkMonitor
import com.example.domain.repository.NetworkMonitor
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.MutableAuthRepository
import com.example.domain.repository.UserSignInCheckRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindMutableAuthRepository(authRepository: AuthRepositoryImpl): MutableAuthRepository

    @Binds
    fun bindUserSignInCheckRepository(authRepository: AuthRepositoryImpl): UserSignInCheckRepository

    @Binds
    fun bindNetworkMonitor(networkMonitor: ConnectivityManagerNetworkMonitor): NetworkMonitor

    companion object {
        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
    }

}
