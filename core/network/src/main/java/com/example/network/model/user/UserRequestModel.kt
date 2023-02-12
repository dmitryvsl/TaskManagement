package com.example.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserRequestModel(
    val email: String,
    val password: String,
)