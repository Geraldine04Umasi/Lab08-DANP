package com.example.gamecatalog.data.repository

import com.example.gamecatalog.BuildConfig
import com.example.gamecatalog.data.model.GameDetail
import com.example.gamecatalog.data.remote.RawgApi
import javax.inject.Inject

class RealGameDetailRepository @Inject constructor(
    private val api: RawgApi
) : GameDetailRepository {

    override suspend fun getGameDetail(id: Int): GameDetail {
        val dto = api.getGameDetail(id, BuildConfig.RAWG_API_KEY)
        return GameDetail(
            id = dto.id,
            title = dto.name,
            description = dto.description ?: "Sin descripción",
            released = dto.released ?: "Fecha desconocida",
            rating = dto.rating,
            imageUrl = dto.backgroundImage,
            genres = dto.genres?.map { it.name } ?: emptyList(),
            platforms = dto.platforms?.map { it.platform.name } ?: emptyList()
        )
    }
}