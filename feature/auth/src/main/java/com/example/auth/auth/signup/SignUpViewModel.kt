package com.example.auth.auth.signup

import androidx.lifecycle.MutableLiveData
import com.example.common.base.BaseViewModel
import com.example.common.base.DataState
import com.example.domain.repository.MutableAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: MutableAuthRepository,
) : BaseViewModel<Boolean>() {


    fun createUser(email: String, password: String) {
        val disposable = makeSingleCall(
            call = authRepository.createUser(email, password),
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