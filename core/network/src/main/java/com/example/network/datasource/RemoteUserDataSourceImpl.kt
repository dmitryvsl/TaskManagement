package com.example.network.datasource

import android.util.Log
import com.example.domain.exception.IncorrectDataException
import com.example.domain.exception.NoInternetException
import com.example.domain.exception.UserAlreadyExist
import com.example.network.model.RemoteUserModel
import com.example.network.model.TokenResponse
import com.example.network.remote.ApiService
import com.example.network.utils.HttpStatusCode
import com.example.network.utils.RetrofitExceptionHandler
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "RemoteUserDataSource"

class RemoteUserDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val exceptionHandler: RetrofitExceptionHandler
) : RemoteUserDataSource {
    override fun createUser(email: String, password: String): Single<TokenResponse> =
        Single.create { emitter->
            apiService
                .createUser(RemoteUserModel(email, password))
                .enqueue(object : retrofit2.Callback<TokenResponse> {
                    override fun onResponse(
                        call: Call<TokenResponse>,
                        response: Response<TokenResponse>
                    ) {
                        when (response.code()) {
                            HttpStatusCode.Created.code ->
                                emitter.onSuccess(response.body()!!)

                            HttpStatusCode.Conflict.code ->
                                emitter.onError(UserAlreadyExist())

                            else -> emitter.onError(UnknownError())
                        }

                    }

                    override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                        Log.e(TAG, "onFailure: $t")
                        emitter.onError(exceptionHandler.handle(NoInternetException()))
                    }
                })
        }


    override fun signInUser(email: String, password: String): Single<TokenResponse> =
        Single.create { emitter ->
            apiService
                .signinUser(RemoteUserModel(email, password))
                .enqueue(object : retrofit2.Callback<TokenResponse> {
                    override fun onResponse(
                        call: Call<TokenResponse>,
                        response: Response<TokenResponse>
                    ) {
                        Log.d(TAG, "token: ${response.code()} ${response.body()}")

                        when (response.code()) {
                            HttpStatusCode.Ok.code ->
                                emitter.onSuccess(response.body()!!)

                            HttpStatusCode.Forbidden.code ->
                                emitter.onError(IncorrectDataException())

                            else -> emitter.onError(UnknownError())
                        }
                    }

                    override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                        Log.e(TAG, "onFailure: $t")
                        emitter.onError(exceptionHandler.handle(t))
                    }
                })

        }

    override fun sendResetPasswordEmail(email: String): Single<Boolean> = Single.just(true)
}