package com.example.domain.repository

import com.example.domain.model.Project
import io.reactivex.Single

interface ProjectRepository {

    fun fetchProjectInfo(workspace: String): Single<Project>
}