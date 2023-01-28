package com.example.data.repository

import com.example.cache.datasource.LocalUserDataSource
import com.example.data.model.asDomain
import com.example.domain.model.Page
import com.example.domain.model.Project
import com.example.domain.repository.ProjectRepository
import com.example.network.datasource.RemoteProjectDataSource
import com.example.network.model.FetchProjectType
import io.reactivex.Single
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteProjectDataSource,
    private val localDataSource: LocalUserDataSource
) : ProjectRepository {

    override fun fetchProjects(page: Page): Single<List<Project>> {
        val token = localDataSource.getToken()
        return remoteDataSource.fetchProjects(
            token, page.page, page.limit, type = FetchProjectType.ALL
        ).map { single -> single.map { project -> project.asDomain() } }
    }

    override fun fetchBookmarks(page: Page): Single<List<Project>> {
        val token = localDataSource.getToken()
        return remoteDataSource.fetchProjects(
            token, page.page, page.limit, type = FetchProjectType.COMPLETED
        ).map { single -> single.map { project -> project.asDomain() } }
    }

    override fun fetchCompleted(page: Page): Single<List<Project>> {
        val token = localDataSource.getToken()
        return remoteDataSource.fetchProjects(
            token, page.page, page.limit, type = FetchProjectType.BOOKMARK
        ).map { single -> single.map { project -> project.asDomain() } }
    }

}