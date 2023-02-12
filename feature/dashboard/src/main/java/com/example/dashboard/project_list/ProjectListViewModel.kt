package com.example.dashboard.project_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.base.BaseViewModel
import com.example.common.base.DataState
import com.example.common.extension.replace
import com.example.domain.exception.InformationNotFound
import com.example.domain.model.Page
import com.example.domain.model.Project
import com.example.domain.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    fun onBookmarkClick(project: Project) {
        if (project.isBookmarked) {
            viewModelScope.launch(Dispatchers.IO) { repository.deleteBookmark(project.id) }
            updateBookmarkStateInProject(project, false)
            return
        }

        viewModelScope.launch(Dispatchers.IO) { repository.addBookmark(project.id) }
        updateBookmarkStateInProject(project, true)
        return
    }

    private fun updateBookmarkStateInProject(project: Project, addToBookmark: Boolean) {
        if (_bookmarkedProjects.value is DataState.Success) {
            val bookmarks = if (addToBookmark) {
                val items =
                    (_bookmarkedProjects.value as DataState.Success<List<Project>>).data.toMutableList()
                items.add(project)
                items
            } else (_bookmarkedProjects.value as DataState.Success<List<Project>>).data.toMutableList()
                .filter { bookmarkProject -> bookmarkProject != project }

            if (bookmarks.isEmpty()) _bookmarkedProjects.value =
                DataState.Error(InformationNotFound())
            else _bookmarkedProjects.value = DataState.Success(bookmarks)
        }
        if (_bookmarkedProjects.value is DataState.Error && addToBookmark) _bookmarkedProjects.value =
            DataState.Success(listOf(project.copy(isBookmarked = true)))

        if (_completedProjects.value is DataState.Success) {
            _completedProjects.value = DataState.Success(
                addRemoveBookmarkToList(
                    project, (data.value as DataState.Success).data, addToBookmark
                )
            )
        }
        if (data.value is DataState.Success) {
            setData(
                DataState.Success(
                    addRemoveBookmarkToList(
                        project, (data.value as DataState.Success).data, addToBookmark
                    )
                )
            )
        }
    }

    private fun addRemoveBookmarkToList(
        project: Project, list: List<Project>, addToBookmark: Boolean
    ): List<Project> {
        val updatedProject = project.copy(isBookmarked = addToBookmark)
        return list.replace(updatedProject) { it == project }
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
