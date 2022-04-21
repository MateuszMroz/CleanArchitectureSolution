package com.example.cleanarchitecturesolution.features.character.details

import androidx.lifecycle.ViewModel
import com.example.cleanarchitecturesolution.features.character.all.presentation.model.CharacterDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CharacterDetailsViewModel(characterId: Int) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterDetailsUiState())
    val uiState: StateFlow<CharacterDetailsUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(errorMessage = "Not implemented: character_id = $characterId")
        }
    }
}
