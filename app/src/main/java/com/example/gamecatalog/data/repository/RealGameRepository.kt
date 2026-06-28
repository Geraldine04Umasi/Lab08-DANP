package com.example.gamecatalog.data.repository

import com.example.gamecatalog.BuildConfig
import com.example.gamecatalog.data.model.Game
import com.example.gamecatalog.data.remote.RawgApi
import com.example.gamecatalog.data.remote.dto.RawgGameDto
import javax.inject.Inject

class RealGameRepository @Inject constructor(
    private val api: RawgApi
) : GameRepository {

    override suspend fun getGames(): List<Game> {
        val response = api.getGames(apiKey = BuildConfig.RAWG_API_KEY)
        return response.results.map { it.toGame() }
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