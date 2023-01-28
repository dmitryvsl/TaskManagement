package com.example.dashboard.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.common.BaseViewModel
import com.example.common.DataState
import com.example.domain.model.Page
import com.example.domain.model.Project
import com.example.domain.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class DashboardHomeViewModel @Inject constructor(
    private val projectRepository: ProjectRepository
) : BaseViewModel<Project>() {

    override val data: MutableLiveData<DataState<Project>> = MutableLiveData()
    override fun setData(value: DataState<Project>) {
        data.value = value
    }

    init {
        fetchProject()
    }

    fun fetchProject() {
        val disposable = makeSingleCall(
            call = projectRepository.fetchProjects(Page()).map { projects -> projects.last() },
            onSuccess = { value -> setData(DataState.Success(value)) },
            onError = { e -> setData(DataState.Error(e)) }
        )
        compositeDisposable.add(disposable)
    }
}