package com.example.network.model

import kotlinx.serialization.Serializable

@Serializable
data class BookmarkModel(
    val token: String,
    val projectId: Int
)

@Serializable
data class StringResponse(
    val message: String,
)
