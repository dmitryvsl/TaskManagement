package com.example.domain.repository

import com.example.domain.model.DataState
import com.example.domain.model.User

interface ProfileRepository {

    suspend fun fetchInfo(): DataState<User>

    suspend fun createWorkspace(name: String, email: String): DataState<Boolean>
}