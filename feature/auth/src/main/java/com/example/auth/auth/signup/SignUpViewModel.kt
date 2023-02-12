package com.example.auth.auth.signup

import com.example.common.base.BaseViewModel
import com.example.domain.model.DataState
import com.example.domain.repository.MutableAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: MutableAuthRepository,
) : BaseViewModel<Boolean>() {


    fun createUser(email: String, password: String) {
        makeSingleCall(
            call = { authRepository.createUser(email, password) },
            onSuccess = { data -> setData(DataState.Success(data)) },
            onError = { e -> setData(DataState.Error(e)) }
        )
    }

    override val data: MutableStateFlow<DataState<Boolean>> = MutableStateFlow(DataState.Initial())

    override fun setData(value: DataState<Boolean>) {
        data.value = value
    }
}