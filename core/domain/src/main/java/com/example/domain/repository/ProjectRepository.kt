package com.example.domain.repository

import com.example.domain.model.Page
import com.example.domain.model.Project
import io.reactivex.Observable
import io.reactivex.Single

interface ProjectRepository {

    fun fetchProjects(page: Page): Single<List<Project>>

    fun fetchBookmarks(page: Page): Single<List<Project>>

    fun fetchCompleted(page: Page): Single<List<Project>>
}