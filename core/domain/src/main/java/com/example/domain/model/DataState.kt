package com.example.domain.model

sealed class DataState<T> {

    class Initial<T> : DataState<T>()
    class Success<T>(val data: T) : DataState<T>()
    class Error<T>(val t: Throwable) : DataState<T>()
    class Loading<T> : DataState<T>()
}