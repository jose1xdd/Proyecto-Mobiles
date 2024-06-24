package com.ufps.proyectomobile.apis.dtos

import com.google.gson.annotations.SerializedName


data class Task(
    val id: Int = 0,
    val name:String,
    val description:String,
    val priority: Int,
    val completed: Boolean =false
)
data class TaskDto(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name:String,
    @SerializedName("description")
    val description:String,
    @SerializedName("priority")
    val priority: Int,
    @SerializedName("completed")
    val completed: Boolean =false

)
data class TaskResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<TaskDto>
)

data class TaskUpdate(
    val completed: Boolean
)

fun TaskDto.toModel() = Task(
    id = this.id,
    name =this.name,
    description =this.description,
    priority =this.priority,
    completed = this.completed
)