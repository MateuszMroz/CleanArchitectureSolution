package com.example.cleanarchitecturesolution.features.location.presentation

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecturesolution.core.exception.ErrorMapper
import com.example.cleanarchitecturesolution.features.location.domain.GetLocationsUseCase
import com.example.cleanarchitecturesolution.features.location.domain.model.Location
import com.example.cleanarchitecturesolution.features.location.presentation.model.LocationDisplayable
import com.example.cleanarchitecturesolution.features.location.presentation.model.LocationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// FIXME(Rewrite to MVI)
// FIXME(Pagination)
class LocationViewModel(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val errorMapper: ErrorMapper,
) : ViewModel(), DefaultLifecycleObserver {

    private val _uiState = MutableStateFlow(LocationUiState())
    val uiState: StateFlow<LocationUiState> = _uiState.asStateFlow()

    // FIXME(During configuration changing fetching data is call again)
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        fetchLocations()
    }

    private fun fetchLocations() {
        setLoadingState(isLoading = true)
        getLocationsUseCase(
            params = Unit,
            scope = viewModelScope,
        ) { result ->
            setLoadingState(isLoading = false)
            result.onSuccess { setLocationsState(it) }
            result.onFailure { handleError(it) }
        }
    }

    private fun setLocationsState(locations: List<Location>) {
        _uiState.update {
            it.copy(locationItems = locations.map { location ->
                LocationDisplayable(location)
            })
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        _uiState.update {
            it.copy(isFetchingLocation = isLoading)
        }
    }

    private fun handleError(throwable: Throwable) {
        _uiState.update { it.copy(errorMessage = errorMapper.map(throwable)) }
    }
}
