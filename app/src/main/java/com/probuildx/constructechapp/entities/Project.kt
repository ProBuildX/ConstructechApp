package com.probuildx.constructechapp.entities

data class Project(
    val id: Int? = null,
    val title: String,
    val description: String,
    val address: String,
    val date: String,
    val budget: String,
    val userId: Int
)