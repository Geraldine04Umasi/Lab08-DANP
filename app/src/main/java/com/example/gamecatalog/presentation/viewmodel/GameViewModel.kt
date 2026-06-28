package com.example.gamecatalog.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamecatalog.di.FakeUseCase
import com.example.gamecatalog.di.RealUseCase
import com.example.gamecatalog.domain.usecase.GetGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    @RealUseCase private val realUseCase: GetGamesUseCase,
    @FakeUseCase private val fakeUseCase: GetGamesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<GameUiState>(GameUiState.Loading)
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private val _useFake = MutableStateFlow(false)
    val useFake: StateFlow<Boolean> = _useFake.asStateFlow()

    init {
        loadGames()
    }

    fun loadGames() {
        viewModelScope.launch {
            _uiState.value = GameUiState.Loading
            val useCase = if (_useFake.value) fakeUseCase else realUseCase
            try {
                val games = useCase()
                _uiState.value = GameUiState.Success(games)
            } catch (e: Exception) {
                _uiState.value = GameUiState.Error(e.message ?: "Ocurrió un error inesperado")
            }
        }
    }

    fun toggleSource() {
        _useFake.value = !_useFake.value
        loadGames()
    }
}