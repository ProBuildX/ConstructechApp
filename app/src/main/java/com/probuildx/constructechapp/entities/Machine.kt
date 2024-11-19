package com.probuildx.constructechapp.entities

data class Machine (
    val id: Int? = null,
    val name: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val totalCost: String,
    val projectId: Int
)