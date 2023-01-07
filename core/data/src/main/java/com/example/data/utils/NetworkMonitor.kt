package com.example.data.utils

import androidx.lifecycle.LiveData
import io.reactivex.Observable

interface NetworkMonitor {
    val isOnline: Observable<Boolean>
}
