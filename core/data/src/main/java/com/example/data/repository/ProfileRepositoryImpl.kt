package com.example.data.repository

import com.example.cache.datasource.LocalUserDataSource
import com.example.data.model.asDomain
import com.example.domain.model.User
import com.example.domain.repository.ProfileRepository
import com.example.network.datasource.RemoteUserDataSource
import io.reactivex.Single
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val dataSource: RemoteUserDataSource,
    private val localDataSource: LocalUserDataSource
) : ProfileRepository {

    override fun fetchInfo(): Single<User> = dataSource.fetchUserInfo(token())
        .map { user -> user.asDomain() }

    override fun createWorkspace(name: String, email: String): Single<Boolean> =
        dataSource.createWorkspace(token(), name, email)

    private fun token() = localDataSource.getToken()
}