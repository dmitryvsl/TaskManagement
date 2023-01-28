package com.example.data.model

import com.example.domain.model.Project
import com.example.network.model.NetworkProject

fun NetworkProject.asDomain() = Project(
    title = this.name,
    startDate = this.startDate,
    endDate = this.endDate,
    tasks = this.tasks.map { task -> task.asDomain() },
    members = this.members.map { user -> user.asDomain() }
)