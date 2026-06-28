package com.example.gamecatalog.presentation.viewmodel

import com.example.gamecatalog.data.model.Game

sealed class GameUiState {
    object Loading : GameUiState()
    data class Success(val games: List<Game>) : GameUiState()
    data class Error(val message: String) : GameUiState()
}