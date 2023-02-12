package com.example.network.datasource

import com.example.network.model.project.FetchProjectType
import com.example.network.model.project.ProjectResponseModel
import io.reactivex.Single

interface RemoteProjectDataSource {

    fun fetchProjects(token: String, startAt: Int, limit: Int, type: FetchProjectType): Single<List<ProjectResponseModel>>

    fun addBookmark(token: String, projectId: Int)
    fun deleteBookmark(token: String, projectId: Int)

}