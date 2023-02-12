package com.example.data.repository

import com.example.cache.datasource.LocalUserDataSource
import com.example.data.model.asDomain
import com.example.domain.model.DataState
import com.example.domain.model.User
import com.example.domain.repository.ProfileRepository
import com.example.network.datasource.RemoteUserDataSource
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val dataSource: RemoteUserDataSource,
    private val localDataSource: LocalUserDataSource
) : ProfileRepository {

    private fun token() = localDataSource.getToken()
    override suspend fun fetchInfo(): DataState<User> {
        var response = dataSource.fetchUserInfo(token())

        if (response is DataState.Error) return DataState.Error(response.t)
        response = response as DataState.Success
        return DataState.Success(response.data.asDomain())
    }

    override suspend fun createWorkspace(name: String, email: String): DataState<Boolean> {
        var response = dataSource.createWorkspace(token(), name, email)

        if (response is DataState.Error) return DataState.Error(response.t)
        response = response as DataState.Success
        return DataState.Success(response.data)
    }
}