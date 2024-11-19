package com.probuildx.constructechapp.services

import com.probuildx.constructechapp.entities.Material
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MaterialsService {

    @GET("materials/{id}")
    suspend fun getById(@Path("id") id: Int): Material

    @GET("projects/{id}/materials")
    suspend fun getByProject(@Path("id") id: Int): List<Material>

    @POST("materials")
    suspend fun create(@Body material: Material): Material

    @DELETE("materials/{id}")
    suspend fun delete(@Path("id") id: Int)

}