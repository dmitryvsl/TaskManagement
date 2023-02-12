package com.example.domain.repository

import com.example.domain.model.User
import io.reactivex.Single

interface ProfileRepository {

    fun fetchInfo(): Single<User>

    fun createWorkspace(name: String, email: String): Single<Boolean>
}