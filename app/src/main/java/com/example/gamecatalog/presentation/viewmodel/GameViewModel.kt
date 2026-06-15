package com.example.gamecatalog.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gamecatalog.data.model.Game
import com.example.gamecatalog.di.FakeUseCase
import com.example.gamecatalog.di.RealUseCase
import com.example.gamecatalog.domain.usecase.GetGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    @RealUseCase private val realUseCase: GetGamesUseCase,  // inyectado por Hilt
    @FakeUseCase private val fakeUseCase: GetGamesUseCase   // inyectado por Hilt
) : ViewModel() {

    // StateFlow de la lista de juegos
    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games: StateFlow<List<Game>> = _games.asStateFlow()

    // StateFlow que indica qué modo está activo
    private val _useFake = MutableStateFlow(false)
    val useFake: StateFlow<Boolean> = _useFake.asStateFlow()

    init {
        loadGames()
    }

    fun loadGames() {
        val useCase = if (_useFake.value) fakeUseCase else realUseCase
        _games.value = useCase()
    }

    fun toggleSource() {
        _useFake.value = !_useFake.value
        loadGames()
    }
}