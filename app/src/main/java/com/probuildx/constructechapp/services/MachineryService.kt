package com.probuildx.constructechapp.services

import com.probuildx.constructechapp.entities.Machine
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MachineryService {

    @GET("machinery/{id}")
    suspend fun getById(@Path("id") id: Int): Machine

    @GET("projects/{id}/machinery")
    suspend fun getByProject(@Path("id") id: Int): List<Machine>

    @POST("machinery")
    suspend fun create(@Body machine: Machine): Machine

    @DELETE("machinery/{id}")
    suspend fun delete(@Path("id") id: Int)
}