package com.example.data.di

import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.ProjectRepositoryImpl
import com.example.domain.repository.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.google.firebase.firestore.FirebaseFirestoreSettings


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
    fun bindProjectRepository(projectRepository: ProjectRepositoryImpl): ProjectRepository

    companion object {
        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

        @Provides
        @Singleton
        fun provideFirebaseDatabase(): FirebaseFirestore =
            FirebaseFirestore.getInstance()
    }

}