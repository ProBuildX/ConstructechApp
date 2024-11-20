package com.probuildx.constructechapp.services

import com.probuildx.constructechapp.entities.Team
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TeamsService {

    @GET("teams/{id}")
    suspend fun getById(@Path("id") id: Int): Team

    @GET("projects/{id}/teams")
    suspend fun getByProject(@Path("id") id: Int): List<Team>

    @POST("teams")
    suspend fun create(@Body material: Team): Team

    @DELETE("teams/{id}")
    suspend fun delete(@Path("id") id: Int)
}