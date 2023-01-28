package com.example.network.datasource

import android.util.Log
import com.example.domain.exception.InformationNotFound
import com.example.network.model.FetchProjectType
import com.example.network.model.NetworkProject
import com.example.network.model.RemoteProjectModel
import com.example.network.remote.ApiService
import com.example.network.utils.HttpStatusCode
import com.example.network.utils.RetrofitExceptionHandler
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "RemoteProjectDSImpl"

class RemoteProjectDataSourceImpl @Inject constructor(
    private val apiService: ApiService, private val exceptionHandler: RetrofitExceptionHandler
) : RemoteProjectDataSource {

    override fun fetchProjects(
        token: String, startAt: Int, limit: Int, type: FetchProjectType
    ): Single<List<NetworkProject>> = Single.create { emitter ->
        val model = RemoteProjectModel(token, startAt, limit)
        val call = when (type) {
            FetchProjectType.ALL -> apiService.fetchProjects(model)
            FetchProjectType.COMPLETED -> apiService.fetchCompletedProjects(model)
            FetchProjectType.BOOKMARK -> apiService.fetchBookmarkProjects(model)
        }

        call.enqueue(object : Callback<List<NetworkProject>> {
            override fun onResponse(
                call: Call<List<NetworkProject>>, response: Response<List<NetworkProject>>
            ) {
                if (response.code() == HttpStatusCode.Ok.code) {
                    if (response.body().isNullOrEmpty())
                        emitter.onError(InformationNotFound())
                    else emitter.onSuccess(response.body()!!)
                }
                Log.e(TAG, "onResponse: ${response.code()} ${response.body()}")
                emitter.onError(UnknownError())
            }

            override fun onFailure(call: Call<List<NetworkProject>>, t: Throwable) {
                Log.e(TAG, "onFailure: $t")
                emitter.onError(exceptionHandler.handle(t))
            }
        })
    }

}