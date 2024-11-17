package com.probuildx.constructechapp.services

import com.probuildx.constructechapp.entities.Project
import com.probuildx.constructechapp.entities.Worker
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProjectService {
    @GET("projects")
    suspend fun getAll(): List<Project>

    @GET("projects/{id}")
    suspend fun get(@Path("id") id: Int): Project

    @POST("projects")
    suspend fun create(@Body project: Project): Project

    @PUT("projects/{id}")
    suspend fun update(@Path("id") id: Int, @Body post: Project): Project

    @DELETE("projects/{id}")
    suspend fun delete(@Path("id") id: Int)

    @GET("projects/{id}/workers")
    suspend fun getWorkers(@Path("id") id: Int): List<Worker>
}