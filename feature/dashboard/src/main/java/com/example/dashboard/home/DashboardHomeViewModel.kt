package com.example.dashboard.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.BaseViewModel
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
) : BaseViewModel<Project>() {

    override val error: MutableLiveData<Throwable> = MutableLiveData()
    override val data: MutableLiveData<Project> = MutableLiveData()
    override val loading: MutableLiveData<Boolean> = MutableLiveData()
    override var disposable: Disposable? = null

    init {
        fetchProject()
    }

    fun fetchProject() {
        makeSingleCall(
            projectRepository.fetchProjects("Capi creative").map { projects -> projects.last() })
    }

    override fun setLoadingValue(value: Boolean) {
        loading.value = value
    }

    override fun setErrorValue(value: Throwable?) {
        error.value = value
    }

    override fun setData(value: Project?) {
        data.value = value
    }

}