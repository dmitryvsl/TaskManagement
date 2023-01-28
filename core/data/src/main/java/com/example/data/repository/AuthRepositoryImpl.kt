package com.example.data.repository

import com.example.cache.datasource.LocalUserDataSource
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.MutableAuthRepository
import com.example.domain.repository.UserSignInCheckRepository
import com.example.network.datasource.RemoteUserDataSource
import io.reactivex.Single
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val localUserDataSource: LocalUserDataSource
) : MutableAuthRepository, AuthRepository, UserSignInCheckRepository {
    override fun createUser(email: String, password: String): Single<Boolean> =
        remoteUserDataSource.createUser(email, password)
            .doOnSuccess { response -> localUserDataSource.saveToken(response.token) }
            // return true which means user created successfully
            .map { true }


    override fun signInUser(email: String, password: String): Single<Boolean> =
        remoteUserDataSource.signInUser(email, password)
            .doOnSuccess { response -> localUserDataSource.saveToken(response.token) }
            // return true which means user signed-in successfully
            .map { true }

    override fun sendResetPasswordEmail(email: String): Single<Boolean> =
        remoteUserDataSource.sendResetPasswordEmail(email)

    override fun isSignedIn(): Boolean = localUserDataSource.isTokenSaved()


}