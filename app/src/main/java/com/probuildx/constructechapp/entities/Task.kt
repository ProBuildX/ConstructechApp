package com.probuildx.constructechapp.entities

data class Task(
    val id: Int? = null,
    val title: String,
    val description: String,
    val startDate: String,
    val projectId: Int,
    val teamId: Int
)
