package com.probuildx.constructechapp.services

import com.probuildx.constructechapp.entities.Task
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TasksService {

    @GET("tasks/{id}")
    suspend fun getById(@Path("id") id: Int): Task

    @GET("projects/{id}/tasks")
    suspend fun getByProject(@Path("id") id: Int): List<Task>

    @POST("tasks")
    suspend fun create(@Body task: Task): Task

    @DELETE("tasks/{id}")
    suspend fun delete(@Path("id") id: Int)

}