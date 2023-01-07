package com.example.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _user: MutableLiveData<FirebaseUser> = MutableLiveData()
    val user: LiveData<FirebaseUser> = _user

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable> = _error

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private var disposable: Disposable? = null

    fun createUser(login: String, password: String) {
        _loading.value = true
        disposable = authRepository.createUser(login, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnEvent { _, _ -> _loading.value = false }
            .subscribeWith(object : DisposableSingleObserver<FirebaseUser>() {
                override fun onSuccess(firebaseUser: FirebaseUser) {
                    Log.d("TAG", "createUser2: $firebaseUser")
                    _user.value = firebaseUser
                }

                override fun onError(e: Throwable) {
                    _error.value = e
                }
            })
    }

    fun cancelUserCreation() {
        disposable?.dispose()
        _loading.value = false
    }

    fun clearError() {
        _error.value = null
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}