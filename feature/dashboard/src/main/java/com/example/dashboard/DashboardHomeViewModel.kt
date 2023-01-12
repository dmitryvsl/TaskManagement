package com.example.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Project
import com.example.domain.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardHomeViewModel @Inject constructor(
    private val projectRepository: ProjectRepository
) : ViewModel() {

    private val _project: MutableLiveData<Project> = MutableLiveData()
    var project: LiveData<Project> = _project

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable> = _error

    private var disposable: Disposable? = null

    init {
        fetchProject()
    }

    fun fetchProject() {
        _error.value = null
        val job = viewModelScope.launch {
            delay(300L)
            _loading.value = true
        }
        disposable = projectRepository
            .fetchProjectInfo("Capi creative2")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnEvent { _, _ ->
                job.cancel()
                _loading.value = false
            }
            .subscribeWith(object : DisposableSingleObserver<Project>() {
                override fun onSuccess(project: Project) {
                    _project.value = project
                }

                override fun onError(e: Throwable) {
                    _error.value = e
                }

            })
    }

}