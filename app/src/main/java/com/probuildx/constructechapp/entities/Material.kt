package com.probuildx.constructechapp.entities

data class Material (
    val id: Int? = null,
    val name: String,
    val description: String,
    val amount: String,
    val receptionDate: String,
    val totalCost: String,
    val projectId: Int
)