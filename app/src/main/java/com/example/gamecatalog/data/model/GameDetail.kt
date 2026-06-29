package com.example.gamecatalog.data.model

data class GameDetail(

    val id: Int,

    val title: String,

    val description: String,

    val released: String,

    val rating: Float,

    val imageUrl: String?,

    val genres: List<String>,

    val platforms: List<String>

)