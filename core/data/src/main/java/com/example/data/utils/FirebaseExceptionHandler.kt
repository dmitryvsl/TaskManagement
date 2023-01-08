package com.example.data.utils

import com.example.domain.exception.*
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import javax.inject.Inject

class FirebaseExceptionHandler @Inject constructor() {
    /***
     * Map Firebase Exception to Domain Layer Exception
     */
    fun handleException(t: Throwable): Throwable {
        return when (t) {
            is FirebaseNetworkException -> NoInternetException()
            is FirebaseAuthUserCollisionException -> UserAlreadyExist()
            is FirebaseAuthInvalidCredentialsException -> InvalidEmailOrPasswordException()
            is FirebaseAuthInvalidUserException -> UserNotExist()
            else -> t
        }
    }
}