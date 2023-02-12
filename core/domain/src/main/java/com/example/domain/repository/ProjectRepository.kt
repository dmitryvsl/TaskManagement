package com.example.domain.repository

import com.example.domain.model.DataState
import com.example.domain.model.Page
import com.example.domain.model.Project

interface ProjectRepository {

    suspend fun fetchProjects(page: Page): DataState<List<Project>>

    suspend fun fetchBookmarks(page: Page): DataState<List<Project>>

    suspend fun fetchCompleted(page: Page): DataState<List<Project>>

    suspend fun addBookmark(projectId: Int)
    suspend fun deleteBookmark(projectId: Int)
}