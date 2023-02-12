package com.example.dashboard.home

import com.example.common.base.BaseViewModel
import com.example.domain.model.DataState
import com.example.domain.model.Page
import com.example.domain.model.Project
import com.example.domain.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardHomeViewModel @Inject constructor(
    private val projectRepository: ProjectRepository
) : BaseViewModel<Project>() {

    override val data: MutableStateFlow<DataState<Project>> = MutableStateFlow(DataState.Loading())
    override fun setData(value: DataState<Project>) {
        data.value = value
    }

    init {
        fetchProject()
    }

    fun fetchProject() {
        makeSingleCall(
            call = { projectRepository.fetchProjects(Page()) },
            onSuccess = { data ->
                setData(DataState.Success(data[0]))
            },
            onError = { t -> setData(DataState.Error(t)) }
        )
    }
}