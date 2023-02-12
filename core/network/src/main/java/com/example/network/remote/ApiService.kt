package com.example.network.remote

import com.example.network.model.BookmarkModel
import com.example.network.model.StringResponse
import com.example.network.model.TokenResponse
import com.example.network.model.project.ProjectRequestModel
import com.example.network.model.project.ProjectResponseModel
import com.example.network.model.user.UserProfileResponseModel
import com.example.network.model.user.UserRequestModel
import com.example.network.model.workspace.CreateWorkspaceRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("user/signin")
    suspend fun signinUser(@Body user: UserRequestModel): Response<TokenResponse>

    @POST("user/create")
    suspend fun createUser(@Body user: UserRequestModel): Response<TokenResponse>

    @POST("/projects")
    suspend fun fetchProjects(@Body model: ProjectRequestModel): Response<List<ProjectResponseModel>>

    @POST("/projects/completed")
    suspend fun fetchCompletedProjects(@Body model: ProjectRequestModel): Response<List<ProjectResponseModel>>

    @POST("/projects/bookmarks")
    suspend fun fetchBookmarkProjects(@Body model: ProjectRequestModel): Response<List<ProjectResponseModel>>

    @POST("/projects/bookmarks/add")
    fun addBookmark(@Body model: BookmarkModel): Call<StringResponse>

    @POST("/projects/bookmarks/delete")
    fun deleteBookmark(@Body model: BookmarkModel): Call<StringResponse>

    @POST("user/info")
    suspend fun fetchUserInfo(@Body tokenResponse: TokenResponse): Response<UserProfileResponseModel>

    @POST("workspace/create")
    suspend fun createWorkspace(@Body model: CreateWorkspaceRequest): Response<UserProfileResponseModel>
}