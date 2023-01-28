package com.example.network.datasource

import com.example.network.model.TokenResponse
import io.reactivex.Single

interface RemoteUserDataSource {

    fun createUser(email: String, password: String): Single<TokenResponse>

    fun signInUser(email: String, password: String): Single<TokenResponse>

    fun sendResetPasswordEmail(email: String): Single<Boolean>

}