package com.example.auth.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.common.BaseViewModel
import com.example.common.DataState
import com.example.domain.repository.MutableAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.Disposable
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
        TODO("Not yet implemented")
    }
}