package com.ufps.proyectomobile.apis.repository

import com.ufps.proyectomobile.apis.Api
import com.ufps.proyectomobile.apis.dtos.Task
import com.ufps.proyectomobile.apis.dtos.TaskResponse
import com.ufps.proyectomobile.apis.dtos.TaskUpdate
import com.ufps.proyectomobile.apis.dtos.Token
import com.ufps.proyectomobile.apis.dtos.User
import com.ufps.proyectomobile.apis.dtos.toModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("NAME_SHADOWING")
class RepositoryImpl : Repository {
    private val BASE_URL = "https://todo-api-50lq.onrender.com/api/"

    private val api: Api

    init {
        val mHttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val mOkHttpClient = OkHttpClient.Builder().addInterceptor(mHttpLoggingInterceptor).build()

        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient).build()

        api = retrofit.create(Api::class.java)
    }

    override suspend fun login(user: User): Token {
        return try {
            val result = api.login(user).awaitResponse()
            if (result.isSuccessful) {
                val token = result.body()?.toModel()
                token ?: throw Exception("Failed to convert response to Token model")
            } else {
                throw Exception("Unsuccessful login response: ${result.errorBody()?.string()}")
            }
        } catch (exception: Exception) {
            // Handle the exception, possibly by logging or throwing it
            throw Exception("Login failed: ${exception.message}", exception)
        }
    }

    override suspend fun createTask(token: String, task: Task): Task {
        return try {
            val result = api.createTask(token, task).awaitResponse()
            if (result.isSuccessful) {
                val task = result.body()?.toModel()
                task ?: throw Exception("Failed to convert response to Task model")
            } else {
                throw Exception("Faild in create new Task")
            }
        } catch (exception: Exception) {
            throw Exception(exception.message)
        }
    }

    override suspend fun getTask(token: String): List<Task> {
        return try {
            val result = api.getTask(token).awaitResponse()
            if (result.isSuccessful) {
                val taskResponse = result.body() ?: TaskResponse(0, null, null, emptyList())
                taskResponse.results.map { it.toModel() }
            } else {
                emptyList()
            }
        } catch (exception: Exception) {
            emptyList()
        }
    }

    override suspend fun deleteTask(token: String, id: Int): Unit {
        try {
            val result = api.deleteTask(token, id).awaitResponse()
            if (!result.isSuccessful) throw Exception("Faild in delete Task")
        } catch (exception: Exception) {
            throw Exception(exception.message)
        }
    }

    override suspend fun updateTask(token: String, id: Int, completed: TaskUpdate): Task {
        return try {
            val result = api.updateTask(token,id,completed).awaitResponse()
            if (result.isSuccessful) {
                val task = result.body()?.toModel()
                task ?: throw Exception("Failed to convert response to Task model")
            } else {
                throw Exception("Faild in update Task")
            }
        } catch (exception: Exception) {
            throw Exception(exception.message)
        }
    }

}
