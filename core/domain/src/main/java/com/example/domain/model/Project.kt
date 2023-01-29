package com.example.domain.model

data class Project(
    val id: Int,
    val title: String,
    val startDate: String,
    val endDate: String,
    val isBookmarked: Boolean,
    val tasks: List<Task>,
    val members: List<User>,
)