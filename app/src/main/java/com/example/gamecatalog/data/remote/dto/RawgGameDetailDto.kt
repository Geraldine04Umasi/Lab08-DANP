package com.example.gamecatalog.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RawgGameDetailDto(

    val id: Int,

    val name: String,

    @SerializedName("description_raw")
    val description: String?,

    val released: String?,

    @SerializedName("background_image")
    val backgroundImage: String?,

    val rating: Float,

    val genres: List<RawgGenreDto>?,

    val platforms: List<RawgPlatformWrapperDto>?

)

data class RawgPlatformWrapperDto(

    val platform: RawgPlatformDto

)

data class RawgPlatformDto(

    val id: Int,

    val name: String

)

