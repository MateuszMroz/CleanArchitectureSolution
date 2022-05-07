package com.example.cleanarchitecturesolution.features.episode.details.presentation

import androidx.lifecycle.ViewModel
import com.example.cleanarchitecturesolution.features.episode.all.presentation.model.EpisodeDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EpisodeDetailsViewModel(episodeId: Int?) : ViewModel() {

    private val _uiState = MutableStateFlow(EpisodeDetailsUiState())
    val uiState: StateFlow<EpisodeDetailsUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(errorMessage = "Not implemented: episode_id = $episodeId")
        }
    }
}
