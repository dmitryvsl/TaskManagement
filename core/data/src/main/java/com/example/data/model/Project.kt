package com.example.data.model

import com.example.domain.model.Project
import com.example.network.model.NetworkProject

fun NetworkProject.asDomain() = Project(
    id = this.id,
    title = this.name,
    startDate = this.startDate,
    endDate = this.endDate,
    isBookmarked = this.isBookmarked,
    tasks = this.tasks.map { task -> task.asDomain() },
    members = this.members.map { user -> user.asDomain() }
)