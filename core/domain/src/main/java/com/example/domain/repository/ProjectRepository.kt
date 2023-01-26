package com.example.domain.repository

import com.example.domain.model.Project
import io.reactivex.Observable
import io.reactivex.Single

interface ProjectRepository {

    fun fetchProjects(workspace: String): Single<List<Project>>

    fun fetchProjectBookmarks(workspace: String): Single<List<String>>
}