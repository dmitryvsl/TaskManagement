package com.example.network.model.project

import kotlinx.serialization.Serializable

@Serializable
data class ProjectRequestModel(
    val token: String,
    val startAt: Int,
    val limit: Int
)
