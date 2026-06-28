package com.example.gamecatalog.data.model

data class Game(
    val id: Int,
    val title: String,
    val genre: String,
    val year: Int,
    val rating: Float,
    val imageUrl: String? = null
)

