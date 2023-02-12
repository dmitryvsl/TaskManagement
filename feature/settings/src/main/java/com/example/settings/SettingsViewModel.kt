package com.example.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.base.BaseViewModel
import com.example.common.base.DataState
import com.example.domain.model.User
import com.example.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: ProfileRepository
) : BaseViewModel<User>() {
    override val data: MutableLiveData<DataState<User>> = MutableLiveData()

    override fun setData(value: DataState<User>) {
        data.value = value
    }

    init {
        viewModelScope.launch {
            setData(DataState.Loading())
            delay(5000L)
            fetchUserInfo()
        }
    }

    fun fetchUserInfo() {
        val disposable = makeSingleCall(
            repository.fetchInfo(),
            onSuccess = { value -> setData(DataState.Success(value)) },
            onError = { e -> setData(DataState.Error(e)) }
        )
        compositeDisposable.add(disposable)
    }

}