package com.example.dashboard.project_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.common.BaseViewModel
import com.example.common.DataState
import com.example.domain.model.Page
import com.example.domain.model.Project
import com.example.domain.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor(
    private val repository: ProjectRepository
) : BaseViewModel<List<Project>>() {

    override val data: MutableLiveData<DataState<List<Project>>> =
        MutableLiveData(DataState.Initial())

    override fun setData(value: DataState<List<Project>>) {
        if (value is DataState.Success) allProjectsPage.setIsLastPage(value.data.size)
        data.value = value
    }

    private val allProjectsPage = Page()
    private val completedProjectsPage = Page()
    private val bookmarkedProjectsPage = Page()

    private val _completedProjects: MutableLiveData<DataState<List<Project>>> =
        MutableLiveData(DataState.Initial())
    val completedProjects: LiveData<DataState<List<Project>>> = _completedProjects

    private val _bookmarkedProjects: MutableLiveData<DataState<List<Project>>> =
        MutableLiveData(DataState.Initial())
    val bookmarkedProjects: LiveData<DataState<List<Project>>> = _bookmarkedProjects


    init {
        fetchProjects()
        fetchCompleted()
        fetchBookmarks()
    }

    fun fetchProjects() {
        val disposable = makeSingleCall(call = repository.fetchProjects(allProjectsPage),
            onSuccess = { value -> setData(DataState.Success(value)) },
            onError = { e -> setData(DataState.Error(e)) })
        compositeDisposable.add(disposable)
    }

    fun fetchBookmarks() {
        val disposable = makeSingleCall(call = repository.fetchBookmarks(bookmarkedProjectsPage),
            onSuccess = { value -> _bookmarkedProjects.value = DataState.Success(value) },
            onError = { e -> _bookmarkedProjects.value = DataState.Error(e) })
        compositeDisposable.add(disposable)
    }

    fun fetchCompleted() {
        val disposable = makeSingleCall(call = repository.fetchCompleted(completedProjectsPage),
            onSuccess = { value -> _completedProjects.value = DataState.Success(value) },
            onError = { e -> _completedProjects.value = DataState.Error(e) })
        compositeDisposable.add(disposable)
    }
}
