package com.example.auth.auth.signin

import androidx.lifecycle.MutableLiveData
import com.example.common.BaseViewModel
import com.example.common.DataState
import com.example.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel<Boolean>() {

    fun signInUser(email: String, password: String) {
        val disposable = makeSingleCall(
            call = authRepository.signInUser(email, password),
            onSuccess = { value -> setData(DataState.Success(value)) },
            onError = { e -> setData(DataState.Error(e)) }
        )
        compositeDisposable.add(disposable)
    }

    override val data: MutableLiveData<DataState<Boolean>> = MutableLiveData()

    override fun setData(value: DataState<Boolean>) {
        data.value = value
    }


}