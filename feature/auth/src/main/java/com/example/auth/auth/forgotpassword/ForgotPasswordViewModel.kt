package com.example.auth.auth.forgotpassword

import androidx.lifecycle.MutableLiveData
import com.example.common.BaseViewModel
import com.example.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<Boolean>() {

    override val error: MutableLiveData<Throwable> = MutableLiveData()
    override val data: MutableLiveData<Boolean> = MutableLiveData()
    override val loading: MutableLiveData<Boolean> = MutableLiveData()
    override var disposable: Disposable? = null

    override fun setLoadingValue(value: Boolean) {
        loading.value = value
    }

    override fun setErrorValue(value: Throwable?) {
        error.value = value
    }

    override fun setData(value: Boolean?) {
        data.value = value
    }

    fun sendForgotPasswordEmail(email: String) {
        makeSingleCall(authRepository.sendResetPasswordEmail(email))
    }

}