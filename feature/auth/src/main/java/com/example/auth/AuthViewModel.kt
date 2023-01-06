package com.example.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _user: MutableLiveData<FirebaseUser> = MutableLiveData()
    val user: LiveData<FirebaseUser> = _user

    private val _error: MutableLiveData<Int> = MutableLiveData()
    val error: LiveData<Int> = _error

    fun createUser(login: String, password: String){
        authRepository
            .createUser(login, password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<FirebaseUser> {
                    override fun onSuccess(firebaseUser: FirebaseUser) {
                        _user.value = firebaseUser
                    }

                    override fun onSubscribe(d: Disposable) {}

                    override fun onError(e: Throwable) {

                    }

                }
            )
    }
}