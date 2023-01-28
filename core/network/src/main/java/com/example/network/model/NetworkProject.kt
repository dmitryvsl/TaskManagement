package com.example.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkProject(
    val id: Int,
    val name: String,
    val startDate: String,
    val endDate: String,
    val tasks: List<NetworkTask>,
    val members: List<NetworkUser>
)

@Serializable
data class NetworkTask(
    val projectId: Int,
    val done: Boolean,
    val color: Long,
    val name: String,
    val priority: Int,
    val endDate: String,
    val actualEndDate: String?,
)

@Serializable
data class NetworkUser(
    val name: String,
    val photo: String,
)
