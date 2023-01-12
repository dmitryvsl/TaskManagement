package com.example.domain.repository

import io.reactivex.Single

interface AuthRepository {

    fun signInUser(email: String, password: String): Single<Boolean>

    fun sendResetPasswordEmail(email: String): Single<Boolean>

}

interface MutableAuthRepository {
    fun createUser(email: String, password: String): Single<Boolean>
}

interface UserSignInCheckRepository{
    fun isSignedIn(): Boolean
}