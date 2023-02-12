package com.example.settings

import com.example.common.base.BaseViewModel
import com.example.domain.model.DataState
import com.example.domain.model.User
import com.example.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: ProfileRepository
) : BaseViewModel<User>() {
    override val data: MutableStateFlow<DataState<User>> = MutableStateFlow(DataState.Initial())

    override fun setData(value: DataState<User>) {
        data.value = value
    }

    init {
        fetchUserInfo()
    }

    fun fetchUserInfo() {
        makeSingleCall(
            call = { repository.fetchInfo() },
            onSuccess = { data ->
                setData(DataState.Success(data))
            },
            onError = { t -> setData(DataState.Error(t)) }
        )
    }
}