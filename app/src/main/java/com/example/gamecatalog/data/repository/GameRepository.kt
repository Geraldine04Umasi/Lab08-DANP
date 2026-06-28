package com.example.gamecatalog.data.repository

import com.example.gamecatalog.data.model.Game

interface GameRepository {
    suspend fun getGames(): List<Game>
}

