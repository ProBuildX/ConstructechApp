package com.probuildx.constructechapp.services

import com.probuildx.constructechapp.entities.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersService {
    @GET("users/{id}")
    suspend fun getById(@Path("id") id: Int): User

    @GET("users")
    suspend fun getByEmail(@Query("email") email: String): List<User>

    @POST("users")
    suspend fun create(@Body user: User): User

    @PUT("users/{id}")
    suspend fun update(@Path("id") id: Int, @Body user: User): User

    @DELETE("users/{id}")
    suspend fun delete(@Path("id") id: Int)
}