package com.example.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RemoteUserModel(
    val email: String,
    val password: String,
)