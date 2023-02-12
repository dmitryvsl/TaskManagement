package com.example.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


abstract class BaseViewModel<T : Any> : ViewModel() {


    abstract val data: LiveData<DataState<T>>
    protected var compositeDisposable: CompositeDisposable = CompositeDisposable()
    protected abstract fun setData(value: DataState<T>)

    fun makeSingleCall(
        call: Single<T>,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit
    ): Disposable {
        setData(DataState.Loading())
        return call
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<T>() {
                override fun onSuccess(value: T) {
                    onSuccess(value)
                }

                override fun onError(e: Throwable) {
                    onError(e)
                }
            })
    }

    fun cancelLoading() {
        compositeDisposable.dispose()
        clearState()
    }

    fun clearState(){
        setData(DataState.Initial())
    }

    override fun onCleared() {
        cancelLoading()
        super.onCleared()
    }
}


sealed class DataState<T> {

    class Initial<T> : DataState<T>()
    class Success<T>(val data: T) : DataState<T>()
    class Error<T>(val t: Throwable) : DataState<T>()
    class Loading<T> : DataState<T>()
}