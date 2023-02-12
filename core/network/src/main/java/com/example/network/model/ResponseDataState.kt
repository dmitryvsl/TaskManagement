package com.example.network.model

sealed class ResponseDataState<T>(val httpStatusCode: Int){

    class Success<T>(httpStatusCode: Int, val data: T?): ResponseDataState<T>(httpStatusCode)

    class Error<T>(httpStatusCode: Int, val t: Throwable): ResponseDataState<T>(httpStatusCode)
}
