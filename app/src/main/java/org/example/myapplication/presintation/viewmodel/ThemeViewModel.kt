package org.example.myapplication.presintation.viewmodel

import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.myapplication.data.repository.PreferencesRepository
import org.example.myapplication.data.repository.UserPreferences
import javax.inject.Inject

sealed class UiState {
    object Loading: UiState()
    data class Success(val themeUiState: ThemeUiState) : UiState()
    data class Error(val message: String) : UiState()
}

data class ThemeUiState(
    val preferences: UserPreferences
)

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val repository: PreferencesRepository
): ViewModel() {
    
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getPreferences().collect { data ->
                _uiState.value = UiState.Success(ThemeUiState(data))
            }
        }
    }

    fun updatePreferences(preferences: UserPreferences) {
        _uiState.update { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    uiState.copy(
                        themeUiState = uiState.themeUiState.copy(
                            preferences = preferences
                        )
                    )
                }
                else -> uiState
            }
        }
    }
}