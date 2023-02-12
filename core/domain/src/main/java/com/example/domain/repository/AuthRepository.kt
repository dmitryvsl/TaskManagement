package com.example.domain.repository

import com.example.domain.model.DataState

interface AuthRepository {

    suspend fun signInUser(email: String, password: String): DataState<Boolean>

    suspend fun sendResetPasswordEmail(email: String): DataState<Boolean>

}

interface MutableAuthRepository {
    suspend fun createUser(email: String, password: String): DataState<Boolean>
}

interface UserSignInCheckRepository{
    fun isSignedIn(): Boolean
}