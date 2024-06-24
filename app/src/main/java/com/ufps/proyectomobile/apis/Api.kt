package com.ufps.proyectomobile.apis

import com.ufps.proyectomobile.apis.dtos.Task
import com.ufps.proyectomobile.apis.dtos.TaskDto
import com.ufps.proyectomobile.apis.dtos.TaskResponse
import com.ufps.proyectomobile.apis.dtos.TaskUpdate
import com.ufps.proyectomobile.apis.dtos.TokenDto
import com.ufps.proyectomobile.apis.dtos.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {
    @POST("tokens/")
    fun login(@Body user: User): Call<TokenDto>

    @POST("tasks/")
    fun createTask(@Header("Authorization") token: String, @Body task: Task): Call<TaskDto>

    @GET("tasks/")
    fun getTask(@Header("Authorization")token:String):Call<TaskResponse>

    @DELETE("tasks/{id}/")
    fun deleteTask(@Header("Authorization") token: String, @Path("id") id: Int): Call<Unit>

    @PATCH("tasks/{id}/")
    fun updateTask(@Header("Authorization") token: String, @Path("id")id: Int,@Body completed: TaskUpdate ): Call<TaskDto>
}
