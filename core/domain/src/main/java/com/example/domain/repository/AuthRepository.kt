package com.example.domain.repository

import com.google.firebase.auth.FirebaseUser
import io.reactivex.Single

interface AuthRepository {

    fun createUser(login: String, password: String): Single<FirebaseUser>
}