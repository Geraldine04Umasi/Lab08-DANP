package com.example.gamecatalog.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RawgGameDto(
    val id: Int,
    val name: String,
    val released: String?,
    @SerializedName("background_image")
    val backgroundImage: String?,
    val rating: Float,
    val metacritic: Int?,
    val genres: List<RawgGenreDto>?
)

data class RawgGenreDto(
    val id: Int,
    val name: String
)