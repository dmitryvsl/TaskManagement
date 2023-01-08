package com.example.data.di

import com.example.data.repository.AuthRepositoryImpl
import com.example.data.utils.ConnectivityManagerNetworkMonitor
import com.example.data.utils.NetworkMonitor
import com.example.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindNetworkMonitor(networkMonitor: ConnectivityManagerNetworkMonitor): NetworkMonitor
    companion object {
        @Provides
        fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
    }

}
