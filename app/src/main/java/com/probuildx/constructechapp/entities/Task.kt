package com.probuildx.constructechapp.entities

data class Task(
    val id: Int?,
    val title: String,
    val description: String,
    val startDate: String,
    val teamId: Int
)
