package com.example.network.datasource

import com.example.network.model.FetchProjectType
import com.example.network.model.NetworkProject
import io.reactivex.Single

interface RemoteProjectDataSource {

    fun fetchProjects(token: String, startAt: Int, limit: Int, type: FetchProjectType): Single<List<NetworkProject>>

    fun addBookmark(token: String, projectId: Int)
    fun deleteBookmark(token: String, projectId: Int)

}