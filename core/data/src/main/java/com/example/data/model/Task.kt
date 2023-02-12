package com.example.data.model

import com.example.domain.model.Task
import com.example.network.model.project.TaskResponseModel

fun TaskResponseModel.asDomain() = Task(
    name = this.name,
    endDate = this.endDate,
    priority = 1,
    done = this.done,
    color = this.color,
)