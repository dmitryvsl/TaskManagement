package com.example.domain.repository

import androidx.lifecycle.LiveData
import io.reactivex.Observable

interface NetworkMonitor {
    val isOnline: Observable<Boolean>
}
