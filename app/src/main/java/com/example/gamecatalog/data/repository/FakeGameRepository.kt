package com.example.gamecatalog.data.repository

import com.example.gamecatalog.data.model.Game
import javax.inject.Inject

class FakeGameRepository @Inject constructor() : GameRepository {

    override suspend fun getGames(genre: String?): List<Game> {
        return listOf(
            Game(1, "Fake Game Alpha", "TEST", 2000, 1.0f),
            Game(2, "Fake Game Beta", "TEST", 2001, 2.0f),
            Game(3, "Fake Game Gamma", "TEST", 2002, 3.0f),
            Game(4, "Fake Game Delta", "TEST", 2003, 4.0f),
            Game(5, "Fake Game Epsilon", "TEST", 2004, 5.0f),
        )
    }
}

