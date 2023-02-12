package com.example.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


abstract class BaseViewModel<T : Any> : ViewModel() {

    abstract val data: StateFlow<DataState<T>>

    private var job: Job? = null
    protected abstract fun setData(value: DataState<T>)

    fun <S> makeSingleCall(
        call: suspend () -> DataState<S>,
        onSuccess: (S) -> Unit,
        onError: (Throwable) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ){
        job = viewModelScope.launch(dispatcher) {
            setData(DataState.Loading())
            var response = call.invoke()
            if (response is DataState.Error) {
                onError(response.t)
                return@launch
            }

            response = response as DataState.Success
            onSuccess(response.data)
        }
    }

    fun clearState() {
        setData(DataState.Initial())
    }

    fun cancelJob(){
        job?.cancel()
    }
}