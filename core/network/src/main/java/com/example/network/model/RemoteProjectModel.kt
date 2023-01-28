package com.example.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RemoteProjectModel(
    val token: String,
    val startAt: Int,
    val limit: Int
)
