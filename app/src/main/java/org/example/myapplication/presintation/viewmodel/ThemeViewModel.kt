package org.example.myapplication.presintation.viewmodel

import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class ThemeUiState(
    val isDarkMode: Boolean = false,
    val dynamicColorsEnabled: Boolean = false,
    val accentColor: Int = 0x0
)

@HiltViewModel
class ThemeViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(ThemeUiState())
    val uiState = _uiState.asStateFlow()

    fun updateMode(mode: Boolean) {
        _uiState.update {
            it.copy(isDarkMode = mode)
        }
    }

    fun updateColor(color: Int) {
        _uiState.update {
            it.copy(accentColor = color)
        }
    }
}