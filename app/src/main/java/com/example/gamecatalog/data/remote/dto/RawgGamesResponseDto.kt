package com.example.gamecatalog.data.remote.dto

data class RawgGamesResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<RawgGameDto>
)