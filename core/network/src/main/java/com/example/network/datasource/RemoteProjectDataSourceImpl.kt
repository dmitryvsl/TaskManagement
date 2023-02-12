package com.example.network.datasource

import com.example.domain.exception.InformationNotFound
import com.example.domain.exception.UserNotInWorkspace
import com.example.domain.model.DataState
import com.example.network.model.BookmarkModel
import com.example.network.model.ResponseDataState
import com.example.network.model.project.FetchProjectType
import com.example.network.model.project.ProjectRequestModel
import com.example.network.model.project.ProjectResponseModel
import com.example.network.remote.ApiService
import com.example.network.utils.HttpStatusCode
import javax.inject.Inject

class RemoteProjectDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteProjectDataSource, BaseDataSource() {

    override suspend fun addBookmark(token: String, projectId: Int) {
        apiService.addBookmark(BookmarkModel(token, projectId)).execute()
    }

    override suspend fun deleteBookmark(token: String, projectId: Int) {
        apiService.deleteBookmark(BookmarkModel(token, projectId)).execute()
    }

    override suspend fun fetchProjects(
        token: String, startAt: Int, limit: Int, type: FetchProjectType
    ): DataState<List<ProjectResponseModel>> {
        val model = ProjectRequestModel(token, startAt, limit)
        val call = when (type) {
            FetchProjectType.ALL -> apiService.fetchProjects(model)
            FetchProjectType.COMPLETED -> apiService.fetchCompletedProjects(model)
            FetchProjectType.BOOKMARK -> apiService.fetchBookmarkProjects(model)
        }

        return when (val response = makeRequest { call }) {
            is ResponseDataState.Success -> {
                if (response.httpStatusCode == HttpStatusCode.NotAcceptable.code)
                    return DataState.Error(UserNotInWorkspace())
                if (response.data.isNullOrEmpty())
                    return DataState.Error(InformationNotFound())

                DataState.Success(response.data)
            }

            is ResponseDataState.Error -> DataState.Error(response.t)
        }
    }
}