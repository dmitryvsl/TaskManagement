package com.example.dashboard.project_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.BaseViewModel
import com.example.domain.model.Project
import com.example.domain.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor(
    private val repository: ProjectRepository
) : BaseViewModel<List<Project>>() {
    override val error: MutableLiveData<Throwable> = MutableLiveData()
    override val data: MutableLiveData<List<Project>> = MutableLiveData()
    override val loading: MutableLiveData<Boolean> = MutableLiveData()
    override var disposable: Disposable? = null

    private var _currentTab: MutableLiveData<Tab> = MutableLiveData(Tab.INITIAL)
    val currentTab: LiveData<Tab> = _currentTab

    private var allProjects: MutableList<Project>? = null
    private var completedProjects: List<Project>? = null
    private var bookmarkedProjects: List<Project>? = null

    private var needFetchBookmarks = false

    override fun setLoadingValue(value: Boolean) {
        loading.value = value
    }

    override fun setErrorValue(value: Throwable?) {
        error.value = value
    }

    override fun setData(value: List<Project>?) {
        if (allProjects == null && value != null) allProjects = value.toMutableList()

        if (completedProjects == null && value != null) {
            completedProjects = value.filter { project ->
                val tasks = project.tasks
                val completedTaskCount = tasks.filter { task -> task.done }.size

                project.tasks.size == completedTaskCount
            }
        }

        if (needFetchBookmarks) {
            needFetchBookmarks = false
            fetchBookmarks()
            return
        }

        data.value = value
    }

    init {
        fetchProjects()
    }

    fun fetchProjects() {
        makeSingleCall(repository.fetchProjects("Capi creative"))
    }

    fun onPageChange(page: Tab) {
        _currentTab.value = page
        if (!allProjects.isNullOrEmpty() && disposable != null) cancelLoading()

        when (page) {
            Tab.INITIAL -> data.value = allProjects
            Tab.COMPLETED -> data.value = completedProjects
            Tab.FLAG -> fetchBookmarks()
        }
    }

    private fun fetchBookmarks() {
        if (allProjects == null) {
            needFetchBookmarks = true
            return
        }

        setData(null)
        makeSingleCall(
            call = repository.fetchProjectBookmarks(workspace = "Capi creative"),
            onSuccess = { projectIds ->
                bookmarkedProjects = allProjects!!.filter { project -> project.title in projectIds }
                setData(bookmarkedProjects)
            },
            onError = { e -> setErrorValue(e) }
        )
    }
}