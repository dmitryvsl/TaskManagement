package com.example.network.datasource

import android.database.Observable
import com.example.domain.model.Project
import com.example.network.model.FetchProjectType
import com.example.network.model.NetworkProject
import io.reactivex.Single

interface RemoteProjectDataSource {

    fun fetchProjects(token: String, startAt: Int, limit: Int, type: FetchProjectType): Single<List<NetworkProject>>

}