package com.example.auth.auth.signin

import com.example.common.base.BaseViewModel
import com.example.domain.model.DataState
import com.example.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel<Boolean>() {

    fun signInUser(email: String, password: String) {
        makeSingleCall(
            call = { authRepository.signInUser(email, password) },
            onSuccess = { data -> setData(DataState.Success(data)) },
            onError = { e -> setData(DataState.Error(e)) }
        )
    }

    override val data: MutableStateFlow<DataState<Boolean>> = MutableStateFlow(DataState.Initial())

    override fun setData(value: DataState<Boolean>) {
        data.value = value
    }
}