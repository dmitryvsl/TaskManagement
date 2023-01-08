package com.example.domain.repository

import com.google.firebase.auth.FirebaseUser
import io.reactivex.Single

interface AuthRepository {

    fun createUser(email: String, password: String): Single<Boolean>

    fun signInUser(email: String, password: String): Single<Boolean>

    fun sendResetPasswordEmail(email: String): Single<Boolean>
}