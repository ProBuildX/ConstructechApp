package com.probuildx.constructechapp.services

import com.probuildx.constructechapp.entities.Worker
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WorkersService {
    @GET("workers/{id}")
    suspend fun getById(@Path("id") id: Int): Worker

    @POST("workers")
    suspend fun create(@Body project: Worker): Worker

    @DELETE("workers/{id}")
    suspend fun delete(@Path("id") id: Int)

    @GET("projects/{id}/workers")
    suspend fun getByProject(@Path("id") id: Int): List<Worker>
}