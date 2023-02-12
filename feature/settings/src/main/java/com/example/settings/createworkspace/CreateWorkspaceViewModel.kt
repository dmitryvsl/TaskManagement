package com.example.settings.createworkspace

import androidx.lifecycle.MutableLiveData
import com.example.common.base.BaseViewModel
import com.example.common.base.DataState
import com.example.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateWorkspaceViewModel @Inject constructor(
    private val repository: ProfileRepository
) : BaseViewModel<Boolean>() {
    override val data: MutableLiveData<DataState<Boolean>> = MutableLiveData()

    override fun setData(value: DataState<Boolean>) {
        data.value = value
    }

    fun createWorkspace(name: String, email: String) {
        val disposable = makeSingleCall(
            repository.createWorkspace(name, email),
            onSuccess = { value -> setData(DataState.Success(value)) },
            onError = { e -> setData(DataState.Error(e)) }
        )
        compositeDisposable.add(disposable)
    }
}