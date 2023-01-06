package com.example.common

import androidx.annotation.StringRes
import com.example.core.common.R
import com.example.domain.exception.UserCreationException
import javax.inject.Inject

class ExceptionHandler @Inject constructor() {

    @StringRes
    fun handle(t: Throwable): Int = when (t) {
        is UserCreationException -> R.string.userCreateError
        else -> R.string.someErrorOccured
    }
}


