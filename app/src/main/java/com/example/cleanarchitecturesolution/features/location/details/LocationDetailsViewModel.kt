package com.example.cleanarchitecturesolution.features.location.details

import androidx.lifecycle.ViewModel
import com.example.cleanarchitecturesolution.features.location.all.presentation.model.LocationDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LocationDetailsViewModel(locationId: Int) : ViewModel() {

    private val _uiState = MutableStateFlow(LocationDetailsUiState())
    val uiState: StateFlow<LocationDetailsUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(errorMessage = "Not implemented: location_id = $locationId")
        }
    }
}
