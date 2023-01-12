package com.example.data.repository

import android.util.Log
import com.example.data.utils.FirebaseExceptionHandler
import com.example.domain.exception.UserAuthException
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.MutableAuthRepository
import com.example.domain.repository.UserSignInCheckRepository
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Single
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val exceptionHandler: FirebaseExceptionHandler
) : AuthRepository, MutableAuthRepository, UserSignInCheckRepository {
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
                    emitter.onError(exceptionHandler.handleAuthException(e))
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
                .addOnFailureListener { e -> emitter.onError(exceptionHandler.handleAuthException(e)) }
        }


    override fun sendResetPasswordEmail(email: String): Single<Boolean> =
        Single.create { emitter ->
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener { emitter.onSuccess(true) }
                .addOnFailureListener { e ->
                    Log.d("TAG", "sendResetPasswordEmail: $e")
                    emitter.onError(exceptionHandler.handleAuthException(e))
                }
        }

    override fun isSignedIn(): Boolean = firebaseAuth.currentUser != null
}