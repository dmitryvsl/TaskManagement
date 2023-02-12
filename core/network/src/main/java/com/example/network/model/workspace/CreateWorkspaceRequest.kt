package com.example.network.model.workspace

import kotlinx.serialization.Serializable

@Serializable
data class CreateWorkspaceRequest(
    val token: String,
    val name: String,
    val email: String,
)
