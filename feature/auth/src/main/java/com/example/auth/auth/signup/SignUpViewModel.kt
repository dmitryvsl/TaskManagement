package com.example.auth.auth.signup

import androidx.lifecycle.MutableLiveData
import com.example.common.BaseViewModel
import com.example.domain.repository.MutableAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.Disposable
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: MutableAuthRepository,
) : BaseViewModel<Boolean>() {

    override val error: MutableLiveData<Throwable> = MutableLiveData()
    override val data: MutableLiveData<Boolean>  = MutableLiveData()
    override val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    override var disposable: Disposable? = null

    override fun setLoadingValue(value: Boolean) {
        loading.value = value
    }

    override fun setErrorValue(value: Throwable?) {
        error.value = value
    }

    override fun setData(value: Boolean?) {
        this.data.value = value
    }

    fun createUser(email: String, password: String) {
        makeSingleCall(authRepository.createUser(email, password))
    }
}