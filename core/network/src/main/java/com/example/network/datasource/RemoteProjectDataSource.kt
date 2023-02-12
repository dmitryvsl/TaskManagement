package com.example.network.datasource

import com.example.domain.model.DataState
import com.example.network.model.project.FetchProjectType
import com.example.network.model.project.ProjectResponseModel

interface RemoteProjectDataSource {

    suspend fun fetchProjects(token: String, startAt: Int, limit: Int, type: FetchProjectType): DataState<List<ProjectResponseModel>>

    suspend fun addBookmark(token: String, projectId: Int)
    suspend fun deleteBookmark(token: String, projectId: Int)

}