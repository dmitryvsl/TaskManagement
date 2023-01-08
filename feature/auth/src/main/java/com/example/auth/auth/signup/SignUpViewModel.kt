package com.example.auth.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.auth.auth.BaseAuthViewModel
import com.example.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
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

    fun createUser(email: String, password: String) {
        makeSingleCall(authRepository.createUser(email, password))
    }
}