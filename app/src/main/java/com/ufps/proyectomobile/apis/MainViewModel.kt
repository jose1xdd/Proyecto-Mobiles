package com.ufps.proyectomobile.apis

import androidx.lifecycle.ViewModel
import com.ufps.proyectomobile.apis.dtos.Task
import com.ufps.proyectomobile.apis.dtos.TaskUpdate
import com.ufps.proyectomobile.apis.dtos.Token
import com.ufps.proyectomobile.apis.dtos.User
import com.ufps.proyectomobile.apis.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: Repository) : ViewModel() {

    suspend fun login(user: User): Token {
        return withContext(Dispatchers.IO) {
            repository.login(user)
        }
    }

    suspend fun createTask(token: String, task: Task): Task {
        return withContext(Dispatchers.IO) {
            repository.createTask(token, task)
        }
    }

    suspend fun getTasks(token: String): List<Task> {
        return withContext(Dispatchers.IO) {
            repository.getTask(token)
        }
    }

    suspend fun deleteTask(token: String, id: Int): Unit {
        return withContext(Dispatchers.IO) {
            repository.deleteTask(token, id)
        }
    }

    suspend fun UpdateTaskActivity(token: String, id: Int, complete: TaskUpdate) {
        return withContext(Dispatchers.IO) {
            repository.updateTask(token, id, complete)
        }
    }
}
