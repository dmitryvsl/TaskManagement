package com.example.auth.auth.forgotpassword

import com.example.common.base.BaseViewModel
import com.example.domain.model.DataState
import com.example.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<Boolean>() {


    fun sendForgotPasswordEmail(email: String) {
        makeSingleCall(
            call = { authRepository.sendResetPasswordEmail(email) },
            onSuccess = { value -> setData(DataState.Success(value)) },
            onError = { e -> setData(DataState.Error(e)) }
        )
    }

    override val data: MutableStateFlow<DataState<Boolean>> = MutableStateFlow(DataState.Initial())

    override fun setData(value: DataState<Boolean>) {
        data.value = value
    }

}