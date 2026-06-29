package com.example.gamecatalog.data.repository

import com.example.gamecatalog.BuildConfig
import com.example.gamecatalog.data.model.Game
import com.example.gamecatalog.data.remote.RawgApi
import com.example.gamecatalog.data.remote.dto.RawgGameDto
import javax.inject.Inject

class RealGameRepository @Inject constructor(
    private val api: RawgApi
) : GameRepository {

    private val genreToSlug = mapOf(
        "Action" to "action",
        "RPG" to "role-playing-games-rpg",
        "Adventure" to "adventure",
        "Shooter" to "shooter"
    )

    override suspend fun getGames(genre: String?): List<Game> {
        val genreSlug = genre?.let { genreToSlug[it] }
        val response = api.getGames(
            apiKey = BuildConfig.RAWG_API_KEY,
            genres = genreSlug
        )
        return response.results
            .map { it.toGame() }
            .filter { it.imageUrl != null }
    }

    private fun RawgGameDto.toGame(): Game = Game(
        id = id,
        title = name,
        genre = genres?.firstOrNull()?.name ?: "Sin género",
        year = released?.take(4)?.toIntOrNull() ?: 0,
        rating = rating,
        imageUrl = backgroundImage
    )
}