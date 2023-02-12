package com.example.settings.createworkspace

import com.example.common.base.BaseViewModel
import com.example.domain.model.DataState
import com.example.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateWorkspaceViewModel @Inject constructor(
    private val repository: ProfileRepository
) : BaseViewModel<Boolean>() {
    override val data: MutableStateFlow<DataState<Boolean>> = MutableStateFlow(DataState.Initial())

    override fun setData(value: DataState<Boolean>) {
        data.value = value
    }

    fun createWorkspace(name: String, email: String) {
        makeSingleCall(
            call = { repository.createWorkspace(name, email) },
            onSuccess = { value -> setData(DataState.Success(value)) },
            onError = { e -> setData(DataState.Error(e)) }
        )
    }
}