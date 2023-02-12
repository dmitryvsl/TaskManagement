package com.example.data.repository

import com.example.cache.datasource.LocalUserDataSource
import com.example.data.model.asDomain
import com.example.domain.model.DataState
import com.example.domain.model.Page
import com.example.domain.model.Project
import com.example.domain.repository.ProjectRepository
import com.example.network.datasource.RemoteProjectDataSource
import com.example.network.model.project.FetchProjectType
import com.example.network.model.project.ProjectResponseModel
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteProjectDataSource,
    private val localDataSource: LocalUserDataSource
) : ProjectRepository {

    override suspend fun addBookmark(projectId: Int) {
        remoteDataSource.addBookmark(token(), projectId)
    }

    override suspend fun deleteBookmark(projectId: Int) {
        remoteDataSource.deleteBookmark(token(), projectId)
    }

    override suspend fun fetchProjects(page: Page): DataState<List<Project>> {
        val response = remoteDataSource.fetchProjects(
            token(), page.page, page.limit, type = FetchProjectType.ALL
        )
        return mapDataModelToDomain(response)
    }

    override suspend fun fetchBookmarks(page: Page): DataState<List<Project>> {
        val response = remoteDataSource.fetchProjects(
            token(), page.page, page.limit, type = FetchProjectType.BOOKMARK
        )
        return mapDataModelToDomain(response)
    }

    override suspend fun fetchCompleted(page: Page): DataState<List<Project>> {
        val response = remoteDataSource.fetchProjects(
            token(), page.page, page.limit, type = FetchProjectType.COMPLETED
        )
        return mapDataModelToDomain(response)
    }

    private fun mapDataModelToDomain(data: DataState<List<ProjectResponseModel>>): DataState<List<Project>> {
        if (data is DataState.Error) return DataState.Error(data.t)
        return DataState.Success((data as DataState.Success).data.map { responseProject -> responseProject.asDomain() })
    }

    private fun token() = localDataSource.getToken()
}