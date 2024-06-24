package com.ufps.proyectomobile.apis.repository

import com.ufps.proyectomobile.apis.dtos.Task
import com.ufps.proyectomobile.apis.dtos.TaskUpdate
import com.ufps.proyectomobile.apis.dtos.Token
import com.ufps.proyectomobile.apis.dtos.User

interface Repository {
    suspend fun login(user: User): Token
    suspend fun createTask(token: String, task: Task): Task
    suspend fun getTask(token: String): List<Task>
    suspend fun deleteTask(token: String, id: Int): Unit
    suspend fun updateTask(token: String,id: Int,completed: TaskUpdate):Task
}