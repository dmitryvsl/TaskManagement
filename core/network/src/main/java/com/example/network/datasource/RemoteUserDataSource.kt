package com.example.network.datasource

import com.example.domain.model.DataState
import com.example.network.model.TokenResponse
import com.example.network.model.user.UserProfileResponseModel

interface RemoteUserDataSource {

    suspend fun createUser(email: String, password: String): DataState<TokenResponse>

    suspend fun signInUser(email: String, password: String): DataState<TokenResponse>

    suspend fun sendResetPasswordEmail(email: String): DataState<Boolean>

    suspend fun fetchUserInfo(token: String): DataState<UserProfileResponseModel>

    suspend fun createWorkspace(token: String, name: String, email: String): DataState<Boolean>

}