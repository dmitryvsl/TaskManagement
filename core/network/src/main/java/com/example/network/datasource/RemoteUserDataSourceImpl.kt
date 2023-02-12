package com.example.network.datasource

import com.example.domain.exception.IncorrectDataException
import com.example.domain.exception.UserAlreadyExist
import com.example.domain.model.DataState
import com.example.network.model.ResponseDataState
import com.example.network.model.TokenResponse
import com.example.network.model.user.UserProfileResponseModel
import com.example.network.model.user.UserRequestModel
import com.example.network.model.workspace.CreateWorkspaceRequest
import com.example.network.remote.ApiService
import com.example.network.utils.HttpStatusCode
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteUserDataSource, BaseDataSource() {

    override suspend fun createUser(email: String, password: String): DataState<TokenResponse> {
        val response = makeRequest { apiService.createUser(UserRequestModel(email, password)) }
        return when (response) {
            is ResponseDataState.Error -> DataState.Error(response.t)
            is ResponseDataState.Success -> {
                if (response.httpStatusCode == HttpStatusCode.Conflict.code)
                    return DataState.Error(UserAlreadyExist())

                DataState.Success(response.data!!)
            }
        }
    }

    override suspend fun signInUser(email: String, password: String): DataState<TokenResponse> {
        val response = makeRequest { apiService.signinUser(UserRequestModel(email, password)) }
        return when (response) {
            is ResponseDataState.Error -> DataState.Error(response.t)
            is ResponseDataState.Success -> {
                if (response.httpStatusCode == HttpStatusCode.Forbidden.code)
                    return DataState.Error(IncorrectDataException())

                DataState.Success(response.data!!)
            }
        }
    }

    override suspend fun sendResetPasswordEmail(email: String): DataState<Boolean> =
        DataState.Success(true)

    override suspend fun fetchUserInfo(token: String): DataState<UserProfileResponseModel> {
        val response = makeRequest { apiService.fetchUserInfo(TokenResponse(token)) }
        return when (response) {
            is ResponseDataState.Error -> DataState.Error(response.t)
            is ResponseDataState.Success -> {
                if (response.data == null)
                    return DataState.Error(UnknownError())

                DataState.Success(response.data)
            }
        }
    }

    override suspend fun createWorkspace(
        token: String, name: String, email: String
    ): DataState<Boolean> {
        val response =
            makeRequest { apiService.createWorkspace(CreateWorkspaceRequest(token, name, email)) }
        return when (response) {
            is ResponseDataState.Error -> DataState.Error(response.t)
            is ResponseDataState.Success -> {
                if (response.data == null)
                    return DataState.Error(UnknownError())

                DataState.Success(true)
            }
        }
    }

}