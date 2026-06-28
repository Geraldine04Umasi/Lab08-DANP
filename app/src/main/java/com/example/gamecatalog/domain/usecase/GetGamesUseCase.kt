package com.example.gamecatalog.domain.usecase

import com.example.gamecatalog.data.model.Game
import com.example.gamecatalog.data.repository.GameRepository
import javax.inject.Inject

class GetGamesUseCase @Inject constructor(
    private val repository: GameRepository
) {
    suspend operator fun invoke(): List<Game> = repository.getGames()
}
