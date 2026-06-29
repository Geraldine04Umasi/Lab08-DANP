package com.example.gamecatalog.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamecatalog.data.model.GameDetail
import com.example.gamecatalog.di.ToggleState
import com.example.gamecatalog.domain.usecase.GetGameDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGameDetailUseCase: GetGameDetailUseCase,
    private val toggleState: ToggleState
) : ViewModel() {

    private val gameId: Int = savedStateHandle.get<Int>("gameId") ?: 0

    private val _uiState = MutableStateFlow<GameDetailUiState>(GameDetailUiState.Loading)
    val uiState: StateFlow<GameDetailUiState> = _uiState.asStateFlow()

    init {
        loadGameDetail()
    }

    fun loadGameDetail() {
        viewModelScope.launch {
            _uiState.value = GameDetailUiState.Loading
            try {
                val detail = getGameDetailUseCase(gameId)
                _uiState.value = GameDetailUiState.Success(detail)
            } catch (e: Exception) {
                _uiState.value = GameDetailUiState.Error(e.message ?: "Error al cargar detalle")
            }
        }
    }
}

sealed class GameDetailUiState {
    object Loading : GameDetailUiState()
    data class Success(val gameDetail: GameDetail) : GameDetailUiState()
    data class Error(val message: String) : GameDetailUiState()
}