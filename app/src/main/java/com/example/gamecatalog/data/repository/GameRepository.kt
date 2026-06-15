package com.example.gamecatalog.data.repository

import com.example.gamecatalog.data.model.Game

interface GameRepository {
    fun getGames(): List<Game>
}

