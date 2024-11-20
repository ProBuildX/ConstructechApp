package com.probuildx.constructechapp.entities

data class Worker(
    val id: Int? = null,
    val name: String,
    val lastName: String,
    val dni: String,
    val role: String,
    val salary: String,
    val projectId: Int,
    val teamId: Int = 0,
)