package com.example.network.remote

import com.example.network.model.BookmarkModel
import com.example.network.model.Response
import com.example.network.model.TokenResponse
import com.example.network.model.project.ProjectRequestModel
import com.example.network.model.project.ProjectResponseModel
import com.example.network.model.user.UserProfileResponseModel
import com.example.network.model.user.UserRequestModel
import com.example.network.model.workspace.CreateWorkspaceRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Accept: application/json")
    @POST("user/signin")
    fun signinUser(@Body user: UserRequestModel): Call<TokenResponse>

    @Headers("Accept: application/json")
    @POST("user/create")
    fun createUser(@Body user: UserRequestModel): Call<TokenResponse>

    @Headers("Accept: application/json")
    @POST("/projects")
    fun fetchProjects(@Body model: ProjectRequestModel): Call<List<ProjectResponseModel>>

    @Headers("Accept: application/json")
    @POST("/projects/completed")
    fun fetchCompletedProjects(@Body model: ProjectRequestModel): Call<List<ProjectResponseModel>>

    @Headers("Accept: application/json")
    @POST("/projects/bookmarks")
    fun fetchBookmarkProjects(@Body model: ProjectRequestModel): Call<List<ProjectResponseModel>>

    @Headers("Accept: application/json")
    @POST("/projects/bookmarks/add")
    fun addBookmark(@Body model: BookmarkModel): Call<Response>

    @Headers("Accept: application/json")
    @POST("/projects/bookmarks/delete")
    fun deleteBookmark(@Body model: BookmarkModel) : Call<Response>

    @POST("user/info")
    fun fetchUserInfo(@Body tokenResponse: TokenResponse): Call<UserProfileResponseModel>

    @POST("workspace/create")
    fun createWorkspace(@Body model: CreateWorkspaceRequest): Call<UserProfileResponseModel>
}