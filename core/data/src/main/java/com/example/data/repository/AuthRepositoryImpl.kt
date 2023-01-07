package com.example.data.repository

import android.util.Log
import com.example.domain.exception.NoInternetException
import com.example.domain.exception.UserAlreadyExist
import com.example.domain.exception.UserCreationException
import com.example.domain.repository.AuthRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Single
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override fun createUser(login: String, password: String): Single<FirebaseUser> {
        return Single.create { emitter ->
            firebaseAuth
                .createUserWithEmailAndPassword(login, password)
                .addOnSuccessListener { task ->
                    Log.d("TAG", "createUser: ${task.user}")
                    if (task.user != null)
                        emitter.onSuccess(task.user!!)
                    else
                        emitter.onError(UserCreationException())
                }
                .addOnFailureListener { e ->
                    when (e){
                        is FirebaseNetworkException -> emitter.onError(NoInternetException())
                        is FirebaseAuthUserCollisionException -> emitter.onError(UserAlreadyExist())
                        else -> emitter.onError(e)
                    }
                }
        }
    }
}