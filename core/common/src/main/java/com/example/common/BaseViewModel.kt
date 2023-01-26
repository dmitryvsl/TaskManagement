package com.example.common

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers


abstract class BaseViewModel<T : Any> : ViewModel() {

    abstract val error: LiveData<Throwable>

    abstract val data: LiveData<T>

    abstract val loading: LiveData<Boolean>

    abstract var disposable: Disposable?

    abstract fun setLoadingValue(value: Boolean)
    protected abstract fun setErrorValue(value: Throwable?)
    protected abstract fun setData(value: T?)

    fun makeSingleCall(
        call: Single<T>,
    ) {
        setErrorValue(null)
        setLoadingValue(value = true)
        disposable = call
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnEvent { _, _ ->
                setLoadingValue(value = false)
                disposable = null
            }
            .subscribeWith(object : DisposableSingleObserver<T>() {
                override fun onSuccess(value: T) {
                    setData(value)
                }

                override fun onError(e: Throwable) {
                    Log.e("lalala", "onError: $e", )
                    setErrorValue(e)
                }
            })

    }

    fun<S : Any> makeSingleCall(
        call: Single<S>,
        onSuccess: (S) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        setErrorValue(null)
        setLoadingValue(value = true)
        disposable = call
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnEvent { _, _ ->
                setLoadingValue(value = false)
                disposable = null
            }
            .subscribeWith(object : DisposableSingleObserver<S>() {
                override fun onSuccess(value: S) {
                    onSuccess(value)
                }

                override fun onError(e: Throwable) {
                    onError(e)
                }
            })

    }


    fun clearError() {
        setErrorValue(null)
    }

    fun cancelLoading() {
        disposable?.dispose()
        disposable = null
        setLoadingValue(false)
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}