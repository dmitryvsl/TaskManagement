package com.example.data.repository

import com.example.cache.datasource.LocalUserDataSource
import com.example.domain.model.DataState
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.MutableAuthRepository
import com.example.domain.repository.UserSignInCheckRepository
import com.example.network.datasource.RemoteUserDataSource
import com.example.network.model.TokenResponse
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val localUserDataSource: LocalUserDataSource
) : MutableAuthRepository, AuthRepository, UserSignInCheckRepository {

    override fun isSignedIn(): Boolean = localUserDataSource.isTokenSaved()
    override suspend fun signInUser(email: String, password: String): DataState<Boolean> =
        mapDataModelToDomainAndSaveToken(remoteUserDataSource.signInUser(email, password))

    override suspend fun sendResetPasswordEmail(email: String): DataState<Boolean> {
        val response = remoteUserDataSource.sendResetPasswordEmail(email)
        if (response is DataState.Error) return DataState.Error(response.t)

        return DataState.Success((response as DataState.Success).data)
    }

    override suspend fun createUser(email: String, password: String): DataState<Boolean> =
        mapDataModelToDomainAndSaveToken(remoteUserDataSource.createUser(email, password))

    private fun mapDataModelToDomainAndSaveToken(data: DataState<TokenResponse>): DataState<Boolean>{
        if (data is DataState.Error) return DataState.Error(data.t)

        localUserDataSource.saveToken((data as DataState.Success).data.token)
        return DataState.Success(true)
    }
}