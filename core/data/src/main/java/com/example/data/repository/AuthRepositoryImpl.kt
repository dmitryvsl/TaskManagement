package com.example.data.repository

import android.util.Log
import com.example.data.utils.FirebaseExceptionHandler
import com.example.domain.exception.InvalidEmailOrPasswordException
import com.example.domain.exception.NoInternetException
import com.example.domain.exception.UserAlreadyExist
import com.example.domain.exception.UserAuthException
import com.example.domain.repository.AuthRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Single
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val exceptionHandler: FirebaseExceptionHandler
) : AuthRepository {
    override fun createUser(email: String, password: String): Single<Boolean> =
        Single.create { emitter ->
            firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { task ->
                    if (task.user != null)
                        emitter.onSuccess(true)
                    else
                        emitter.onError(UserAuthException())
                }
                .addOnFailureListener { e ->
                    emitter.onError(exceptionHandler.handleException(e))
                }
        }

    override fun signInUser(email: String, password: String): Single<Boolean> =
        Single.create { emitter ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    if (result.user != null)
                        emitter.onSuccess(true)
                    else
                        emitter.onError(UserAuthException())
                }
                .addOnFailureListener { e -> emitter.onError(exceptionHandler.handleException(e)) }
        }


    override fun sendResetPasswordEmail(email: String): Single<Boolean> =
        Single.create { emitter ->
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener { emitter.onSuccess(true) }
                .addOnFailureListener { e ->
                    Log.d("TAG", "sendResetPasswordEmail: $e")
                    emitter.onError(exceptionHandler.handleException(e))
                }
        }
}