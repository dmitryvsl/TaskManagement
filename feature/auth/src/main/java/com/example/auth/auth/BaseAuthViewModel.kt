package com.example.auth.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

abstract class BaseAuthViewModel() : ViewModel() {

    abstract val error: LiveData<Throwable>

    abstract val isCallSuccess: LiveData<Boolean>

    abstract val loading: LiveData<Boolean>

    abstract var disposable: Disposable?

    fun cancelUserCreation() {
        disposable?.dispose()
        setLoadingValue(value = false)
    }

    abstract fun setLoadingValue(value: Boolean)
    abstract fun setErrorValue(value: Throwable?)
    abstract fun setIsCallSuccessValue(value: Boolean)

    fun makeSingleCall(
        call: Single<Boolean>
    ) {
        setLoadingValue(value = true)
        disposable = call
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnEvent { _, _ -> setLoadingValue(value = false) }
            .subscribeWith(object : DisposableSingleObserver<Boolean>() {
                override fun onSuccess(value: Boolean) {
                    setIsCallSuccessValue(value)
                }

                override fun onError(e: Throwable) {
                    setErrorValue(e)
                }
            })
    }

    fun clearError(){
        setErrorValue(null)
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}