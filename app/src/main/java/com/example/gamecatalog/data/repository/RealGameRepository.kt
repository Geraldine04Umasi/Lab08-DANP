package com.example.gamecatalog.data.repository

import com.example.gamecatalog.data.model.Game
import javax.inject.Inject

class RealGameRepository @Inject constructor() : GameRepository {

    override fun getGames(): List<Game> = listOf(
        Game(1, "The Legend of Zelda: Tears of the Kingdom", "Aventura", 2023, 4.9f),
        Game(2, "Elden Ring", "RPG", 2022, 4.8f),
        Game(3, "God of War Ragnarök", "Acción", 2022, 4.9f),
        Game(4, "Hollow Knight", "Metroidvania", 2017, 4.7f),
        Game(5, "Hades", "Roguelike", 2020, 4.8f),
        Game(6, "Cyberpunk 2077", "RPG", 2020, 4.2f),
        Game(7, "Red Dead Redemption 2", "Mundo Abierto", 2018, 4.9f),
        Game(8, "Stardew Valley", "Simulación", 2016, 4.8f),
    )
}

