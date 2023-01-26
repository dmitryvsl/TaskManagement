package com.example.data.repository

import android.util.Log
import com.example.data.utils.FirebaseExceptionHandler
import com.example.domain.model.Project
import com.example.domain.model.User
import com.example.domain.repository.ProjectRepository
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject
import com.example.domain.model.Task as ModelTask

class ProjectRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val exceptionHandler: FirebaseExceptionHandler
) : ProjectRepository {

    override fun fetchProjects(workspace: String): Single<List<Project>> =
        Single.create { emitter ->
            val projectTask = fetchFirebaseProjects(workspace)
            Tasks.await(projectTask)
            checkNotException(projectTask.exception) { e -> emitter.onError(e) }

            val projects = projectTask.result.documents.map { document -> mapProject(document) }
                .toMutableList()

            projects.forEachIndexed { index, project ->
                val userIdsTask = fetchMemberUserIds(workspace, project.title)
                Tasks.await(userIdsTask)
                checkNotException(userIdsTask.exception) { e -> emitter.onError(e) }
                val userIds =
                    userIdsTask.result.documents.map { document -> document.getString("UserId")!! }

                val usersTask = fetchUsersById(userIds)
                Tasks.await(usersTask)
                checkNotException(usersTask.exception) { e -> emitter.onError(e) }
                val users = usersTask.result.documents.map { document -> mapUser(document) }

                val tasksTask = fetchTasks(workspace, project.title)
                Tasks.await(tasksTask)
                checkNotException(tasksTask.exception) { e -> emitter.onError(e) }

                val tasks = tasksTask.result.documents.map { document -> mapTask(document) }
                projects[index] = project.copy(tasks = tasks, members = users)
            }
            emitter.onSuccess(projects.toList())
        }
            .onErrorResumeNext { e ->
                Single.error(
                    exceptionHandler.handleFirestoreException(e.cause ?: e)
                )
            }

    override fun fetchProjectBookmarks(workspace: String): Single<List<String>> =
        Single.create { emitter ->
            val bookmarkTask = fetchFirebaseProjectBookmarks(workspace)
            Tasks.await(bookmarkTask)
            checkNotException(bookmarkTask.exception) { e -> emitter.onError(e) }

            val projectsId =
                bookmarkTask.result.documents.map { document -> document.getString("project")!! }
            emitter.onSuccess(projectsId)
        }


    private fun fetchFirebaseProjects(workspace: String): Task<QuerySnapshot> =
        firestore
            .collection("workspaces").document(workspace)
            .collection("projects")
            .get(Source.SERVER)

    private fun fetchTasks(workspace: String, projectTitle: String): Task<QuerySnapshot> =
        firestore
            .collection("workspaces").document(workspace)
            .collection("projects").document(projectTitle)
            .collection("tasks")
            .orderBy("endDate")
            .get(Source.SERVER)

    private fun fetchMemberUserIds(workspace: String, projectTitle: String): Task<QuerySnapshot> =
        firestore
            .collection("workspaces").document(workspace)
            .collection("projects").document(projectTitle)
            .collection("members")
            .get(Source.SERVER)

    private fun fetchUsersById(userIds: List<String>): Task<QuerySnapshot> =
        firestore
            .collection("users")
            .whereIn(FieldPath.documentId(), userIds)
            .get(Source.SERVER)

    private fun fetchFirebaseProjectBookmarks(workspace: String): Task<QuerySnapshot> =
        firestore
            .collection("users").document("UjABz7klCiaE4YV2ymGVqvFtjkJ2")
            .collection("bookmarks")
            .whereEqualTo("workspace", workspace)
            .get()


    private fun mapProject(snapshot: DocumentSnapshot): Project = Project(
        title = snapshot.id,
        startDate = snapshot.getString("startDate")!!,
        endDate = snapshot.getString("endDate")!!,
        tasks = listOf(),
        members = listOf()
    )

    private fun mapTask(snapshot: DocumentSnapshot): ModelTask = ModelTask(
        name = snapshot.id,
        startDate = snapshot.getString("startDate")!!,
        endDate = snapshot.getString("endDate")!!,
        priority = snapshot.getLong("priority")!!,
        done = snapshot.getBoolean("done")!!,
        color = snapshot.getLong("color")!!
    )

    private fun mapUser(snapshot: DocumentSnapshot): User = User(
        id = snapshot.id,
        avatar = snapshot.getString("avatar")!!,
        name = snapshot.getString("name")!!,
        role = snapshot.getString("role")!!
    )

    private fun checkNotException(e: Throwable?, onThrow: (Throwable) -> Unit) {
        if (e != null) onThrow(exceptionHandler.handleFirestoreException(e))
    }
}