package com.example.domain.model

data class Project(
    val title: String,
    val startDate: String,
    val endDate: String,
    val tasks: List<Task>,
    val members: List<User>,
)