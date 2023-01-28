package com.example.data.model

import com.example.domain.model.User
import com.example.network.model.NetworkUser

fun NetworkUser.asDomain() = User(photo, name)