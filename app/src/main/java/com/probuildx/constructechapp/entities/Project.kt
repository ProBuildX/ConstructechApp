package com.probuildx.constructechapp.entities

data class Project(
    val id: Int? = null,
    val title: String,
    val description: String,
    val userId: Int,
)