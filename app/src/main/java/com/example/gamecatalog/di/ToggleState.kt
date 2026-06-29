package com.example.gamecatalog.di

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToggleState @Inject constructor() {
    private val _useFake = MutableStateFlow(false)
    val useFake: StateFlow<Boolean> = _useFake.asStateFlow()

    fun toggle() {
        _useFake.value = !_useFake.value
    }
}