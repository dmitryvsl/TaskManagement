package com.example.data.model

import com.example.domain.model.User
import com.example.domain.model.Workspace
import com.example.network.model.project.UserResponseModel
import com.example.network.model.user.UserProfileResponseModel
import com.example.network.model.user.WorkspaceResponseModel

fun UserResponseModel.asDomain() = User(photo, name, email)

fun UserProfileResponseModel.asDomain() =
    User(
        photo, name, email, this.workspace?.asDomain()
    )

fun WorkspaceResponseModel.asDomain() = Workspace(
    name, email, photo
)