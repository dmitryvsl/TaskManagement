package com.example.auth.auth.signin

import androidx.lifecycle.MutableLiveData
import com.example.auth.auth.BaseAuthViewModel
import com.example.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseAuthViewModel() {

    override val error: MutableLiveData<Throwable> = MutableLiveData()
    override val isCallSuccess: MutableLiveData<Boolean> = MutableLiveData()
    override val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    override var disposable: Disposable? = null

    override fun setLoadingValue(value: Boolean) {
        loading.value = value
    }

    override fun setErrorValue(value: Throwable?) {
        error.value = value
    }

    override fun setIsCallSuccessValue(value: Boolean) {
        isCallSuccess.value = value
    }

    fun signInUser(email: String, password: String) {
        makeSingleCall(authRepository.signInUser(email, password))
    }


}