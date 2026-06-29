package com.example.gamecatalog.domain.usecase

import com.example.gamecatalog.data.model.GameDetail
import com.example.gamecatalog.data.repository.GameDetailRepository
import javax.inject.Inject

class GetGameDetailUseCase @Inject constructor(
    private val repository: GameDetailRepository
) {
    suspend operator fun invoke(id: Int): GameDetail {
        return repository.getGameDetail(id)
    }
}
