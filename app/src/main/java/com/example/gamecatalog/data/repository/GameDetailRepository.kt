package com.example.gamecatalog.data.repository

import com.example.gamecatalog.data.model.GameDetail

interface GameDetailRepository {
    suspend fun getGameDetail(id: Int): GameDetail
}