package com.probuildx.constructechapp.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//object RetrofitClient {
//    val apiService: ProjectService by lazy {
//        Retrofit.Builder()
//            .baseUrl("http://10.0.2.2:3000/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ProjectService::class.java)
//    }
//}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:3000/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val projectsService: ProjectsService by lazy {
        retrofit.create(ProjectsService::class.java)
    }

    val workersService: WorkersService by lazy {
        retrofit.create(WorkersService::class.java)
    }
}