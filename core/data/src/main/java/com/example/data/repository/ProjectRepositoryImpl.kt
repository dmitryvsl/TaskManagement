package com.example.data.repository

import com.example.domain.model.Project
import com.example.domain.model.Task
import com.example.domain.repository.ProjectRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ProjectRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ProjectRepository {
    override fun fetchProjectInfo(workspace: String): Single<Project> {
        return Single.create { emitter ->
            runBlocking {
                    delay(10000)
                try {
                    val project = withContext(Dispatchers.Default) { fetchProject(workspace) }
                    val projectName = project.id
                    val tasks = withContext(Dispatchers.Default) { fetchTasks(workspace, projectName) }
                    emitter.onSuccess(
                        Project(
                            title = projectName,
                            startDate = project.getString("startDate")!!,
                            endDate = project.getString("endDate")!!,
                            tasks = tasks.documents.map { task ->
                                Task(
                                    name = task.id,
                                    startDate = task.getString("startDate")!!,
                                    endDate = task.getString("endDate")!!,
                                    priority = task.getLong("priority")!!,
                                    done = task.getBoolean("done")!!
                                )
                            }
                        )
                    )
                } catch (e: Exception) {
                    emitter.onError(e)
                }
            }

        }
    }

    private suspend fun fetchProject(workspace: String): DocumentSnapshot =
        suspendCoroutine { cont ->
            firestore
                .collection("workspaces").document(workspace)
                .collection("projects")
                .limit(1)
                .get()
                .addOnSuccessListener { result -> cont.resume(result.documents[0]) }
                .addOnFailureListener { e -> cont.resumeWithException(e) }
        }

    private suspend fun fetchTasks(workspace: String, projectTitle: String): QuerySnapshot =
        suspendCoroutine { cont ->
            firestore
                .collection("workspaces").document(workspace)
                .collection("projects").document(projectTitle)
                .collection("tasks")
                .get()
                .addOnSuccessListener { result -> cont.resume(result) }
                .addOnFailureListener { e -> cont.resumeWithException(e) }

        }
}