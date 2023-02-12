package com.example.domain.model

data class User(
    val photo: String?,
    val name: String,
    val email: String,
    val workspace: Workspace? = null
)

data class Workspace(
    val name: String,
    val email: String,
    val photo: String?,
)