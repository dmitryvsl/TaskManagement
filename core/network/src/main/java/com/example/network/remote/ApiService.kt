package com.example.network.remote

import com.example.network.model.BookmarkModel
import com.example.network.model.BookmarkResponse
import com.example.network.model.NetworkProject
import com.example.network.model.RemoteProjectModel
import com.example.network.model.RemoteUserModel
import com.example.network.model.TokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @Headers("Accept: application/json")
    @POST("user/signin")
    fun signinUser(@Body user: RemoteUserModel): Call<TokenResponse>

    @Headers("Accept: application/json")
    @POST("user/create")
    fun createUser(@Body user: RemoteUserModel): Call<TokenResponse>

    @Headers("Accept: application/json")
    @POST("/projects")
    fun fetchProjects(@Body model: RemoteProjectModel): Call<List<NetworkProject>>

    @Headers("Accept: application/json")
    @POST("/projects/completed")
    fun fetchCompletedProjects(@Body model: RemoteProjectModel): Call<List<NetworkProject>>

    @Headers("Accept: application/json")
    @POST("/projects/bookmarks")
    fun fetchBookmarkProjects(@Body model: RemoteProjectModel): Call<List<NetworkProject>>

    @Headers("Accept: application/json")
    @POST("/projects/bookmarks/add")
    fun addBookmark(@Body model: BookmarkModel): Call<BookmarkResponse>

    @Headers("Accept: application/json")
    @POST("/projects/bookmarks/delete")
    fun deleteBookmark(@Body model: BookmarkModel) : Call<BookmarkResponse>
}