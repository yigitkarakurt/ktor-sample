package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: Int,
    val name: String,
    val pageNumber: Int,
)
