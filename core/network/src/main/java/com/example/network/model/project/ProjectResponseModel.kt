package com.example.network.model.project

import kotlinx.serialization.Serializable

@Serializable
data class ProjectResponseModel(
    val id: Int,
    val name: String,
    val startDate: String,
    val endDate: String,
    val isBookmarked: Boolean,
    val tasks: List<TaskResponseModel>,
    val members: List<UserResponseModel>
)

@Serializable
data class TaskResponseModel(
    val projectId: Int,
    val done: Boolean,
    val color: Long,
    val name: String,
    val priority: Int,
    val endDate: String,
    val actualEndDate: String?,
)

@Serializable
data class UserResponseModel(
    val name: String,
    val photo: String,
    val email: String,
)
