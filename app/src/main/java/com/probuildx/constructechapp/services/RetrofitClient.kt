package com.probuildx.constructechapp.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    //private const val BASE_URL = "http://10.0.2.2:3000/"
    private const val BASE_URL = "https://fakeapi-jte0.onrender.com"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val usersService: UsersService by lazy {
        retrofit.create(UsersService::class.java)
    }

    val projectsService: ProjectsService by lazy {
        retrofit.create(ProjectsService::class.java)
    }

    val workersService: WorkersService by lazy {
        retrofit.create(WorkersService::class.java)
    }
}