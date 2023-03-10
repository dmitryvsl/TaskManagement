package com.example.dashboard.project_list

import androidx.lifecycle.viewModelScope
import com.example.common.base.BaseViewModel
import com.example.common.extension.replace
import com.example.domain.exception.InformationNotFound
import com.example.domain.model.DataState
import com.example.domain.model.Page
import com.example.domain.model.Project
import com.example.domain.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor(
    private val repository: ProjectRepository
) : BaseViewModel<List<Project>>() {

    override val data: MutableStateFlow<DataState<List<Project>>> =
        MutableStateFlow(DataState.Initial())

    override fun setData(value: DataState<List<Project>>) {
        if (value is DataState.Success) allProjectsPage.checkIsLastPage(value.data.size)
        data.value = value
    }

    private val allProjectsPage = Page()
    private val completedProjectsPage = Page()
    private val bookmarkedProjectsPage = Page()

    private val _completedProjects: MutableStateFlow<DataState<List<Project>>> =
        MutableStateFlow(DataState.Initial())
    val completedProjects: StateFlow<DataState<List<Project>>> = _completedProjects

    private val _bookmarkedProjects: MutableStateFlow<DataState<List<Project>>> =
        MutableStateFlow(DataState.Initial())
    val bookmarkedProjects: StateFlow<DataState<List<Project>>> = _bookmarkedProjects


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
        makeSingleCall(
            call = { repository.fetchProjects(allProjectsPage) },
            onSuccess = { data -> setData(DataState.Success(data)) },
            onError = { e -> setData(DataState.Error(e)) }
        )
    }

    fun fetchBookmarks() {
        makeSingleCall(
            call = { repository.fetchBookmarks(bookmarkedProjectsPage) },
            onSuccess = { data -> setData(DataState.Success(data)) },
            onError = { e -> setData(DataState.Error(e)) }
        )
    }

    fun fetchCompleted() {
        makeSingleCall(
            call = { repository.fetchCompleted(completedProjectsPage) },
            onSuccess = { data -> setData(DataState.Success(data)) },
            onError = { e -> setData(DataState.Error(e)) }
        )
    }
}
