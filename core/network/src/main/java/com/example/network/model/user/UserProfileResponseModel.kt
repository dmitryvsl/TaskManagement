package com.example.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponseModel(
    val email: String,
    val name: String,
    val photo: String?,
    val workspace: WorkspaceResponseModel?,
)

@Serializable
data class WorkspaceResponseModel(
    val name: String,
    val photo: String?,
    val email: String,
)
