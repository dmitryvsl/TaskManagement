package com.example.domain.model

data class Task(
    val name: String,
    val startDate: String,
    val endDate: String,
    val priority: Long,
    val done: Boolean,
    val color: Long,
)