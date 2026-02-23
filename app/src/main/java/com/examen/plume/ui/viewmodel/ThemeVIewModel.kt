package com.examen.plume.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

class ThemeViewModel : ViewModel() {

    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode

    fun setTheme(mode: ThemeMode) {
        _themeMode.value = mode
    }
}