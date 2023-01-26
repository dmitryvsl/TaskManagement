package com.example.auth.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.common.BaseViewModel
import com.example.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel<Boolean>() {

    override val error: MutableLiveData<Throwable> = MutableLiveData()
    override val data: MutableLiveData<Boolean> = MutableLiveData()
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


    fun signInUser(email: String, password: String) {
        makeSingleCall(authRepository.signInUser(email, password))
    }


}