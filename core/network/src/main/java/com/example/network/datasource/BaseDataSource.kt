package com.example.network.datasource

import com.example.domain.exception.NoInternetException
import com.example.network.model.ResponseDataState
import com.example.network.utils.HttpStatusCode
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

abstract class BaseDataSource {

    suspend fun <T> makeRequest(call: suspend () -> Response<T>): ResponseDataState<T> {
        val response = try {
            call.invoke()
        } catch (e: ConnectException) {
            return ResponseDataState.Error(
                httpStatusCode = HttpStatusCode.Unavailable.code,
                t = NoInternetException()
            )
        } catch (e: SocketTimeoutException) {
            return ResponseDataState.Error(
                httpStatusCode = HttpStatusCode.Unavailable.code,
                t = NoInternetException()
            )
        } catch (e: Exception) {
            return ResponseDataState.Error(
                httpStatusCode = HttpStatusCode.Unavailable.code,
                t = UnknownError()
            )
        }

        return ResponseDataState.Success(response.code(), response.body())
    }
}