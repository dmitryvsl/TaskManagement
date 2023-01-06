package com.example.data.repository

import com.example.domain.exception.UserCreationException
import com.example.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override fun createUser(login: String, password: String): Single<FirebaseUser> {
        return Single.create { emitter ->
            firebaseAuth.createUserWithEmailAndPassword(login, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result.user != null)
                        emitter.onSuccess(task.result.user!!)
                    else
                        emitter.onError(UserCreationException())
                }
        }
    }
}