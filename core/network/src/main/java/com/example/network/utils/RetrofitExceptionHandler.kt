package com.example.network.utils

import com.example.domain.exception.NoInternetException
import java.net.SocketTimeoutException
import javax.inject.Inject

class RetrofitExceptionHandler @Inject constructor()  {

    fun handle(t: Throwable): Throwable = when(t){
        is SocketTimeoutException -> NoInternetException()
        else -> t
    }
}